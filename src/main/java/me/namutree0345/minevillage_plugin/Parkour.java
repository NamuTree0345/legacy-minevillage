package me.namutree0345.minevillage_plugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class Parkour implements Listener {

    public HashMap<UUID, Location> recentCheckpoint = new HashMap<>();

    private void printLoc(Location loc) {
        System.out.printf("(%f, %f, %f), (%d, %d, %d)\n", loc.getX(), loc.getY(), loc.getZ(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        /*
        Location loc = event.getTo();
        loc.setY(loc.getY() - 1);
         */
        Location loc = event.getTo();
        Block loc2 = event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() - 1, event.getPlayer().getLocation().getBlockZ());
        if(recentCheckpoint.containsKey(event.getPlayer().getUniqueId())) {
            if(loc2.getType() == Material.RED_WOOL) {
                event.getPlayer().teleport(recentCheckpoint.get(event.getPlayer().getUniqueId()));
            }
        }
        if(loc2.getType() == Material.GOLD_BLOCK) {
            System.out.print("A: ");
            printLoc(loc.toBlockLocation());
            System.out.print("B: ");
            printLoc(recentCheckpoint.get(event.getPlayer().getUniqueId()));
            if(!recentCheckpoint.containsKey(event.getPlayer().getUniqueId())) {
                recentCheckpoint.put(event.getPlayer().getUniqueId(), loc.toBlockLocation());
                event.getPlayer().sendTitle(ChatColor.GREEN + "시작!", "", 10, 70, 10);
            } else {
                if(recentCheckpoint.get(event.getPlayer().getUniqueId()) != loc.toBlockLocation()) {
                    recentCheckpoint.replace(event.getPlayer().getUniqueId(), loc.toBlockLocation());
                    event.getPlayer().sendTitle(ChatColor.GOLD + "체크포인트 달성!", "", 10, 70, 10);
                    event.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "" + ChatColor.GOLD + "님이 어떤 체크포인트를 찍었습니다!");
                    event.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX(), loc.getY() - 1, loc.getZ(), 100, 3, 3, 3);
                }
            }
        }
    }

}
