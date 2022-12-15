package stanuwu.fragmentcore2.helpers;

import org.bukkit.Location;
import org.bukkit.Material;
import stanuwu.fragmentcore2.FragmentCore2;

import java.util.*;

import static java.util.Map.entry;

public class Helper {
    public static final List<Material> stackContents = Arrays.asList(
            Material.WHITE_CONCRETE_POWDER,
            Material.ORANGE_CONCRETE_POWDER,
            Material.MAGENTA_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER,
            Material.YELLOW_CONCRETE_POWDER,
            Material.LIME_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER,
            Material.PURPLE_CONCRETE_POWDER,
            Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.GREEN_CONCRETE_POWDER,
            Material.RED_CONCRETE_POWDER,
            Material.BLACK_CONCRETE_POWDER,
            Material.WHITE_CONCRETE,
            Material.ORANGE_CONCRETE,
            Material.MAGENTA_CONCRETE,
            Material.LIGHT_BLUE_CONCRETE,
            Material.YELLOW_CONCRETE,
            Material.LIME_CONCRETE,
            Material.PINK_CONCRETE,
            Material.GRAY_CONCRETE,
            Material.LIGHT_GRAY_CONCRETE,
            Material.CYAN_CONCRETE,
            Material.PURPLE_CONCRETE,
            Material.BLUE_CONCRETE,
            Material.BROWN_CONCRETE,
            Material.GREEN_CONCRETE,
            Material.RED_CONCRETE,
            Material.BLACK_CONCRETE,
            Material.SAND,
            Material.RED_SAND,
            Material.GRAVEL,
            Material.ANVIL
    );

    public static final List<Material> buttonsSlow = Arrays.asList(
            Material.BIRCH_BUTTON,
            Material.ACACIA_BUTTON,
            Material.CRIMSON_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.OAK_BUTTON,
            Material.DARK_OAK_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.WARPED_BUTTON
    );

    public static List<Material> buttonsFast = Arrays.asList(
            Material.POLISHED_BLACKSTONE_BUTTON,
            Material.STONE_BUTTON
    );

    public static final List<Material> fallingBlocks = Arrays.asList(
            Material.WHITE_CONCRETE_POWDER,
            Material.ORANGE_CONCRETE_POWDER,
            Material.MAGENTA_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER,
            Material.YELLOW_CONCRETE_POWDER,
            Material.LIME_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER,
            Material.PURPLE_CONCRETE_POWDER,
            Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.GREEN_CONCRETE_POWDER,
            Material.RED_CONCRETE_POWDER,
            Material.BLACK_CONCRETE_POWDER,
            Material.SAND,
            Material.RED_SAND,
            Material.GRAVEL,
            Material.ANVIL
    );

    public static final Material CMS_SUB = Material.BEACON;

    public static final Map<Material, Material> msExchange = new EnumMap<>(Map.ofEntries(
            entry(Material.SAND, Material.SANDSTONE),
            entry(Material.RED_SAND, Material.RED_SANDSTONE),
            entry(Material.GRAVEL, Material.ANDESITE),
            entry(Material.ANVIL, Material.IRON_BLOCK),
            entry(Material.WHITE_CONCRETE_POWDER, CMS_SUB),
            entry(Material.ORANGE_CONCRETE_POWDER, CMS_SUB),
            entry(Material.MAGENTA_CONCRETE_POWDER, CMS_SUB),
            entry(Material.LIGHT_BLUE_CONCRETE_POWDER, CMS_SUB),
            entry(Material.YELLOW_CONCRETE_POWDER, CMS_SUB),
            entry(Material.LIME_CONCRETE_POWDER, CMS_SUB),
            entry(Material.PINK_CONCRETE_POWDER, CMS_SUB),
            entry(Material.GRAY_CONCRETE_POWDER, CMS_SUB),
            entry(Material.LIGHT_GRAY_CONCRETE_POWDER, CMS_SUB),
            entry(Material.CYAN_CONCRETE_POWDER, CMS_SUB),
            entry(Material.PURPLE_CONCRETE_POWDER, CMS_SUB),
            entry(Material.BLUE_CONCRETE_POWDER, CMS_SUB),
            entry(Material.BROWN_CONCRETE_POWDER, CMS_SUB),
            entry(Material.GREEN_CONCRETE_POWDER, CMS_SUB),
            entry(Material.RED_CONCRETE_POWDER, CMS_SUB),
            entry(Material.BLACK_CONCRETE_POWDER, CMS_SUB)
    ));

    public static final Map<Material, Material> msExchangeRev = new EnumMap<>(Map.ofEntries(
            entry(Material.SANDSTONE, Material.SAND),
            entry(Material.RED_SANDSTONE, Material.RED_SAND),
            entry(Material.ANDESITE, Material.GRAVEL),
            entry(Material.IRON_BLOCK, Material.ANVIL)
    ));

    public static final Map<Byte, Material> mscByte = new HashMap<>(Map.ofEntries(
            entry(b(0), Material.WHITE_CONCRETE_POWDER),
            entry(b(1), Material.ORANGE_CONCRETE_POWDER),
            entry(b(2), Material.MAGENTA_CONCRETE_POWDER),
            entry(b(3), Material.LIGHT_BLUE_CONCRETE_POWDER),
            entry(b(4), Material.YELLOW_CONCRETE_POWDER),
            entry(b(5), Material.LIME_CONCRETE_POWDER),
            entry(b(6), Material.PINK_CONCRETE_POWDER),
            entry(b(7), Material.GRAY_CONCRETE_POWDER),
            entry(b(8), Material.LIGHT_GRAY_CONCRETE_POWDER),
            entry(b(9), Material.CYAN_CONCRETE_POWDER),
            entry(b(10), Material.PURPLE_CONCRETE_POWDER),
            entry(b(11), Material.BLUE_CONCRETE_POWDER),
            entry(b(12), Material.BROWN_CONCRETE_POWDER),
            entry(b(13), Material.GREEN_CONCRETE_POWDER),
            entry(b(14), Material.RED_CONCRETE_POWDER),
            entry(b(15), Material.BLACK_CONCRETE_POWDER)
    ));

    public static final Map<Material, Byte> mscMat = new EnumMap<>(Map.ofEntries(
            entry(Material.WHITE_CONCRETE_POWDER, b(0)),
            entry(Material.ORANGE_CONCRETE_POWDER, b(1)),
            entry(Material.MAGENTA_CONCRETE_POWDER, b(2)),
            entry(Material.LIGHT_BLUE_CONCRETE_POWDER, b(3)),
            entry(Material.YELLOW_CONCRETE_POWDER, b(4)),
            entry(Material.LIME_CONCRETE_POWDER, b(5)),
            entry(Material.PINK_CONCRETE_POWDER, b(6)),
            entry(Material.GRAY_CONCRETE_POWDER, b(7)),
            entry(Material.LIGHT_GRAY_CONCRETE_POWDER, b(8)),
            entry(Material.CYAN_CONCRETE_POWDER, b(9)),
            entry(Material.PURPLE_CONCRETE_POWDER, b(10)),
            entry(Material.BLUE_CONCRETE_POWDER, b(11)),
            entry(Material.BROWN_CONCRETE_POWDER, b(12)),
            entry(Material.GREEN_CONCRETE_POWDER, b(13)),
            entry(Material.RED_CONCRETE_POWDER, b(14)),
            entry(Material.BLACK_CONCRETE_POWDER, b(15))
    ));

    public static final List<Material> extraTrackables = Arrays.asList(
            Material.TNT,
            Material.DISPENSER
    );

    public static final List<Material> waterproofBlocks = Arrays.asList(
            Material.REDSTONE_WIRE,
            Material.REDSTONE_TORCH,
            Material.REDSTONE_WALL_TORCH,
            Material.LEVER,
            Material.REPEATER,
            Material.COMPARATOR
    );


    public static String ParsePrefix(String msg) {
        return msg.replaceAll("%prefix%", getPrefix());
    }

    public static String WithPrefix(String msg) {
        return getPrefix() + msg;
    }

    public static String getPrefix() {
        return getConfigString("cosmetic", "message-prefix");
    }

    public static String getConfigString(String path, String name) {
        return FragmentCore2.config.getString("fragmentcore." + path + "." + name);
    }

    private static byte b(int i) {
        return (byte) i;
    }

    public static long locToLong(Location loc) {
        if (loc.getWorld() == null) return 0;
        return (int) loc.getX() ^ (int) loc.getY() ^ (int) loc.getZ() ^ loc.getWorld().getUID().getMostSignificantBits();
    }
}
