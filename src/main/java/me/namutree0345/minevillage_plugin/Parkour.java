package me.namutree0345.minevillage_plugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class Parkour implements Listener {

    public HashMap<UUID, Location> recentCheckpoint = new HashMap<>();

    private void printLoc(Location location) {
        Bukkit.getLogger().info(String.format("(%f, %f, %f), (%d, %d, %d)", location.getX(), location.getY(), location.getZ(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    private boolean checkSamePos(Location a, Location b) {
        return a.getBlockX() == b.getBlockX() && a.getBlockY() == b.getBlockY() && a.getBlockZ() == b.getBlockZ();
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        /*
        Location loc = event.getTo();
        loc.setY(loc.getY() - 1);
         */
        Location loc = event.getTo();
        Block loc2 = event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() - 1, event.getPlayer().getLocation().getBlockZ());
        if(loc2.getType() == Material.REDSTONE_BLOCK) {
            Entity entity = event.getPlayer().getWorld().spawnEntity(new Location(event.getPlayer().getWorld(), 388, 69, -564), EntityType.COW);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Minevillage_plugin.getPlugin(Minevillage_plugin.class), entity::remove, 30);
            event.getPlayer().teleport(event.getFrom());
        }
        if(recentCheckpoint.containsKey(event.getPlayer().getUniqueId())) {
            if(loc2.getType() == Material.RED_WOOL) {
                event.getPlayer().teleport(recentCheckpoint.get(event.getPlayer().getUniqueId()));
            }
        }
        if(loc2.getType() == Material.GOLD_BLOCK) {
            if(!recentCheckpoint.containsKey(event.getPlayer().getUniqueId())) {
                recentCheckpoint.put(event.getPlayer().getUniqueId(), loc);
                event.getPlayer().sendTitle(ChatColor.GREEN + "시작!", "", 10, 70, 10);
            } else {
                printLoc(loc.toBlockLocation());
                printLoc(recentCheckpoint.get(event.getPlayer().getUniqueId()).toBlockLocation());
                Bukkit.getLogger().info(String.valueOf(!checkSamePos(recentCheckpoint.get(event.getPlayer().getUniqueId()).toBlockLocation(), loc.toBlockLocation())));
                if(!checkSamePos(recentCheckpoint.get(event.getPlayer().getUniqueId()).toBlockLocation(), loc.toBlockLocation())) {
                    recentCheckpoint.replace(event.getPlayer().getUniqueId(), loc);
                    event.getPlayer().sendTitle(ChatColor.GOLD + "체크포인트 달성!", "", 10, 70, 10);
                    event.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "" + ChatColor.GOLD + "님이 어떤 체크포인트를 찍었습니다!");
                    event.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX(), loc.getY() - 1, loc.getZ(), 100, 3, 3, 3);
                }
            }
        }
    }

}
