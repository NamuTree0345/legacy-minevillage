package me.namutree0345.minevillage_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NpcesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("이 명령어는 플레이어 말고는 사용이 불가능합니다!");
            return true;
        }
        if(args.length > 1) {
            sender.sendMessage(ChatColor.RED + "사용법: /npces <wand/name/skinowned>");
            return true;
        }
        switch(args[0]) {
            case "wand":
                ((Player) sender).getInventory().addItem(new ItemStack(Material.BONE));
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "사용법: /npces <wand/name/skinowned>");
                return true;
        }
    }
}
