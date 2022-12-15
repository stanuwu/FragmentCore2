package stanuwu.fragmentcore2.features;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import stanuwu.fragmentcore2.FragmentCore2;
import stanuwu.fragmentcore2.helpers.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicSand implements CommandExecutor, Listener, TabCompleter {
    private final FragmentCore2 plugin;
    private final Map<Location, Material> ms = new HashMap<>();
    private final NamespacedKey key;

    public MagicSand(FragmentCore2 plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "mstype");
    }

    ItemStack getSandItem(Material block) {
        ItemStack item = new ItemStack(block, 1);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1530);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Helper.getConfigString("cosmetic", "magicsand-name")));
        item.setItemMeta(meta);
        return item;
    }

    void stackDownReplace(Material initial, Material replacement, Location below) {
        Block adjBlock = below.getBlock().getRelative(BlockFace.DOWN);
        while (adjBlock.getType().equals(initial)) {
            adjBlock.setType(replacement);
            adjBlock = adjBlock.getRelative(BlockFace.DOWN);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (Material m : Helper.fallingBlocks) {
                list.add(m.name().toLowerCase());
            }
            list.add("clear");
            list.add("refill");
        }
        return list;
    }

    public void clearAllMs() {
        ms.forEach((location, material) -> {
            replaceMs(material, location);
            stackDownReplace(material, Material.AIR, location);
        });
    }

    private void replaceMs(Material material, Location loc) {
        if (!Helper.msExchange.containsKey(material)) return;
        Material type = Helper.msExchange.get(material);
        loc.getBlock().setType(type);
        if (type.equals(Helper.CMS_SUB) && loc.getBlock().getState() instanceof TileState c) {
            c.getPersistentDataContainer().set(key, PersistentDataType.BYTE, Helper.mscMat.get(material));
            c.update();
        }
    }

    private boolean placeMsFromPlaceholder(Material material, Location loc) {
        Material type;
        if (material.equals(Helper.CMS_SUB) && loc.getBlock().getState() instanceof TileState c) {
            type = Helper.mscByte.get(c.getPersistentDataContainer().get(key, PersistentDataType.BYTE));
        } else {
            type = Helper.msExchangeRev.get(material);
        }
        if (type == null) return false;
        placeMs(type, loc.getBlock(), loc.getWorld());
        return true;
    }

    private void placeMs(Material type, Block block, World world) {
        Material msBlock = Material.getMaterial(Helper.getConfigString("cannoning", "magicsand-block").toUpperCase());
        if (msBlock == null) return;
        block.setType(msBlock);
        Location msLoc = block.getLocation();
        stackDownReplace(Material.AIR, type, msLoc);
        ms.put(msLoc.getBlock().getLocation(), type);
        new BukkitRunnable() {
            final Location spawnLoc = msLoc.clone().add(0.5, -0.7, 0.5);
            FallingBlock last = null;

            public void run() {
                if (msLoc.getBlock().getType().equals(msBlock) && world != null) {
                    if (msLoc.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                        if (!(last != null && last.getBoundingBox().overlaps(msLoc.getBlock().getBoundingBox()))) {
                            last = world.spawnFallingBlock(spawnLoc, type.createBlockData());
                        }
                    }
                } else {
                    stackDownReplace(type, Material.AIR, msLoc);
                    ms.remove(msLoc.getBlock().getLocation());
                    this.cancel();
                }
            }
        }.runTaskTimer(this.plugin, 0, 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        Material block;
        if (args.length < 1) {
            block = Material.SAND;
        } else if (args[0].equals("clear")) {
            int radius = plugin.getConfig().getInt("fragmentcore.cannoning.magicsand-range");
            Location playerLoc = player.getLocation();
            int count = 0;
            for (int x = -radius; x <= radius; x++)
                for (int y = -radius; y <= radius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        Location msLoc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ()).add(x, y, z);
                        Location key = msLoc.getBlock().getLocation();
                        if (ms.containsKey(key)) {
                            count++;
                            Material msMat = ms.get(key);
                            replaceMs(msMat, msLoc);
                        }
                    }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Cleard " + count + " MagicSand")));
            return true;
        } else if (args[0].equals("refill")) {
            int radius = plugin.getConfig().getInt("fragmentcore.cannoning.magicsand-range");
            Location playerLoc = player.getLocation();
            int count = 0;
            for (int x = -radius; x <= radius; x++)
                for (int y = -radius; y <= radius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        Location msLoc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ()).add(x, y, z);
                        if (placeMsFromPlaceholder(msLoc.getBlock().getType(), msLoc)) count++;
                    }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Refilled " + count + " MagicSand")));
            return true;
        } else if (!Helper.fallingBlocks.contains(Material.getMaterial(args[0].toUpperCase()))) {
            block = Material.SAND;
        } else {
            block = Material.getMaterial(args[0].toUpperCase());
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Received MagicSand")));
        player.getInventory().addItem(getSandItem(block));
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            if (getSandItem(event.getItemInHand().getType()).equals(event.getItemInHand())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Placed MagicSand")));
                placeMs(event.getItemInHand().getType(), event.getBlockPlaced(), event.getPlayer().getWorld());
            }
        }
    }
}
