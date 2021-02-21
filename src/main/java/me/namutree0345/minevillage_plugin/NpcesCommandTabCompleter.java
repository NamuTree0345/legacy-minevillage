package me.namutree0345.minevillage_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class NpcesCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> l = new ArrayList<>();
        if(args.length == 1) {
            l.add("wand");
            l.add("name");
            l.add("skinowned");
            return l;
        }
        return null;
    }
}
