package stanuwu.fragmentcore2.features;

import org.bukkit.*;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import stanuwu.fragmentcore2.FragmentCore2;
import stanuwu.fragmentcore2.helpers.Helper;
import xyz.fragmentmc.fragmentcore.api.events.MultiDispenserEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiDispenser implements CommandExecutor, Listener, TabCompleter {
    final private FragmentCore2 plugin;

    public MultiDispenser(FragmentCore2 plugin) {
        this.plugin = plugin;
    }

    private String getDispenserName(int amount, int fuse) {
        return ChatColor.translateAlternateColorCodes('&', Helper.getConfigString("cosmetic", "multidispenser-name").replace("%amount%", Integer.toString(amount)).replace("%fuse%", Integer.toString(fuse)));
    }

    private ItemStack getDispenser(int amount, int fuse) {
        ItemStack item = new ItemStack(Material.DISPENSER, 1);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1530);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, amount);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, fuse);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(getDispenserName(amount, fuse));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = new ArrayList<>();
        if (args.length == 1) {
            l = new LinkedList<>(Arrays.asList("10", "50", "100", "500", "1000", "5000", "10000", "50000", "100000"));
            int max = FragmentCore2.config.getInt("fragmentcore.cannoning.max-multitnt");
            l.removeIf(s -> Integer.parseInt(s) >= max);
            l.add(Integer.toString(max));
        } else if (args.length == 2) {
            l = Arrays.asList("0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120");
        }
        return l;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        int amount;
        int fuse;
        if (args.length < 2) {
            fuse = 80;
        } else {
            fuse = Integer.parseInt(args[1]);
            if (fuse < 0) {
                fuse = 0;
            }
        }
        if (args.length < 1) {
            amount = 1;
        } else {
            amount = Integer.parseInt(args[0]);
            if (amount < 1) {
                amount = 1;
            }
            int max = FragmentCore2.config.getInt("fragmentcore.cannoning.max-multitnt");
            if (amount > max) {
                amount = max;
            }
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Received MultiDispenser")));
        player.getInventory().addItem(getDispenser(amount, fuse));
        return true;
    }

    private boolean isValidMulti(@Nullable String name) {
        if (name == null) return false;
        return name.replaceAll("(?<!§)\\d{1,7}", "0").equals(getDispenserName(0, 0));
    }

    private int[] getMultiAmount(String name) {
        int[] result = new int[2];
        int idx = 0;
        Matcher matcher = Pattern.compile("(?<!§)\\d{1,7}").matcher(name);
        while (matcher.find()) {
            try {
                result[idx] = Integer.parseInt(name.substring(matcher.start(), matcher.end()));
            } catch (NumberFormatException e) {
                // ignore exception
            }
            idx++;
            if (idx > 1) break;
        }
        return result;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDispense(BlockDispenseEvent event) {
        if (!event.isCancelled() && event.getBlock().getState() instanceof Nameable dispenser) {
            String name = dispenser.getCustomName();
            if (isValidMulti(name)) {
                int[] data = getMultiAmount(name);
                if (data[0] == 0 || data[1] == 0) return;
                World world = event.getBlock().getWorld();
                int amount = data[0];
                int fuse = data[1];
                Location summonLoc = event.getBlock().getRelative(((Directional) event.getBlock().getBlockData()).getFacing()).getLocation().add(0.5, 0, 0.5);
                Material summonMat = event.getItem().getType();
                if (summonMat.equals(Material.TNT)) {
                    boolean hasEventSent = false;
                    for (int i = 0; i < amount; i++) {
                        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(summonLoc, EntityType.PRIMED_TNT);
                        if (!hasEventSent) {
                            hasEventSent = true;
                            MultiDispenserEvent newEvent = new MultiDispenserEvent(false, event.getBlock(), tnt, amount, fuse);
                            plugin.getServer().getPluginManager().callEvent(newEvent);
                            if (event.isCancelled()) {
                                tnt.remove();
                                return;
                            }
                        }
                        tnt.setFuseTicks(fuse);
                    }
                    event.setCancelled(true);
                } else if (Helper.fallingBlocks.contains(summonMat)) {
                    boolean hasEventSent = false;
                    for (int i = 0; i < amount; i++) {
                        FallingBlock fallingBlock = world.spawnFallingBlock(summonLoc, summonMat.createBlockData());
                        if (!hasEventSent) {
                            hasEventSent = true;
                            MultiDispenserEvent newEvent = new MultiDispenserEvent(false, event.getBlock(), fallingBlock, amount, fuse);
                            plugin.getServer().getPluginManager().callEvent(newEvent);
                            if (event.isCancelled()) {
                                fallingBlock.remove();
                                return;
                            }
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            if (event.getBlock().getType() == Material.DISPENSER) {
                Map<Enchantment, Integer> enchants = event.getItemInHand().getEnchantments();
                int amount = enchants.getOrDefault(Enchantment.PROTECTION_EXPLOSIONS, 0);
                int fuse = enchants.getOrDefault(Enchantment.DURABILITY, 0);
                if (event.getItemInHand().equals(getDispenser(amount, fuse))) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix(String.format("Placed MultiDispenser [Amount: %s, Fuse:%s]", amount, fuse))));
                    if (event.getBlock().getState() instanceof Nameable dispenser)
                        dispenser.setCustomName(getDispenserName(amount, fuse));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (event.getBlock().getType().equals(Material.DISPENSER)) {
                if (event.getBlock().getState() instanceof Nameable dispenser && isValidMulti(dispenser.getCustomName())) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("Broke MultiDispenser")));
                }
            }
        }
    }
}