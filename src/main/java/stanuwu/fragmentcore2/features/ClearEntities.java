package stanuwu.fragmentcore2.features;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import stanuwu.fragmentcore2.FragmentCore2;
import stanuwu.fragmentcore2.helpers.Helper;

public class ClearEntities implements CommandExecutor {

    public ClearEntities() {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        int r = FragmentCore2.config.getInt("fragmentcore.cannoning.clearentity-range");
        int tnt = 0;
        int falling = 0;
        int item = 0;
        for(Entity entity : player.getNearbyEntities(r, r, r)) {
            if(entity instanceof TNTPrimed) {
                tnt ++;
                entity.remove();
            }
            else if (entity instanceof FallingBlock) {
                falling ++;
                entity.remove();
            } else if(entity instanceof Item) {
                item++;
                entity.remove();
            }
        }
        if(tnt+falling+item<1){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Helper.WithPrefix("No Entities Found")));
        }else{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Helper.WithPrefix("Entities Cleared!")));
            if (tnt > 0) player.sendMessage(ChatColor.translateAlternateColorCodes('&',Helper.WithPrefix("TNT: " + tnt)));
            if (falling > 0) player.sendMessage(ChatColor.translateAlternateColorCodes('&',Helper.WithPrefix("Falling Block: " + falling)));
            if (item > 0) player.sendMessage(ChatColor.translateAlternateColorCodes('&',Helper.WithPrefix("Item: " + item)));
        }
        return true;
    }
}