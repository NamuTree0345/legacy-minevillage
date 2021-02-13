package me.namutree0345.minevillage_plugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Extensions implements Listener {
    @EventHandler
    public void onServerlistPing(ServerListPingEvent event) {

        // Mod - ServerListPing
        LocalDate currentDate = LocalDate.now();
        event.setMaxPlayers(Integer.parseInt(currentDate.format(DateTimeFormatter.ofPattern("YYYYMMDD"))));
        event.setMotd(ChatColor.GREEN + "                        MineVillage\n" + ChatColor.GOLD + "                  마인마을" + ChatColor.WHITE + "로 어서오세요!");
    }

    @EventHandler
    public void configuratePlayerAndNotification(PlayerJoinEvent event) throws IOException {
        Minevillage_plugin.configuration.initUserConfiguration(event.getPlayer().getUniqueId().toString());
        String recentNotice = (String) Minevillage_plugin.configuration.loadConfiguration().get("notice");
        if(recentNotice == null) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "최근 공지가 없습니다.");
            return;
        }
        event.getPlayer().sendMessage(ChatColor.GREEN + "최근 공지: " + ChatColor.RESET + recentNotice);
    }
}
