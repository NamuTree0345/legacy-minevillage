package me.namutree0345.minevillage_plugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EmeraldCollector implements Listener {

    @EventHandler
    public void pickupInventory(InventoryPickupItemEvent event) {
        System.out.println("pickup");
        if(event.getItem().getItemStack().getType() == Material.EMERALD) {
//jj
            event.getInventory().remove(event.getItem().getItemStack());
        }
    }

    @EventHandler
    public void collectEvent(PlayerAttemptPickupItemEvent event) throws IOException, NotInitiallizedConfigurationException {
        if(event.getItem().getItemStack().getType() == Material.EMERALD) {
            System.out.println(event.getRemaining());
            //event.setCancelled(true);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            int eutut = event.getItem().getItemStack().getAmount();
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "마인마을 코인 +" + eutut + "을 얻었습니다!"));
            YamlConfiguration yConf = Minevillage_plugin.configuration.loadConfiguration();
            ConfigurationSection usersSection = yConf.getConfigurationSection("users");
            ConfigurationSection thatUser = usersSection.getConfigurationSection(event.getPlayer().getUniqueId().toString());
            thatUser.set("coin", ((int)thatUser.get("coin")) + 1);
            yConf.save(Minevillage_plugin.configuration.conf);
            event.getPlayer().getInventory().remove(Material.EMERALD);
        }
    }

    public ItemStack getItemStack(Material is, String name) {
        ItemStack a = new ItemStack(is);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(name);
        a.setItemMeta(b);
        return a;
    }

    public ItemStack getSkullStack(Player owner, String name, ArrayList<String> lore) {
        ItemStack a = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta b = (SkullMeta) a.getItemMeta();
        b.setPlayerProfile(owner.getPlayerProfile());
        b.setDisplayName(name);
        a.setItemMeta(b);
        return a;
    }

    @EventHandler
    public void collectMenuHandler(InventoryClickEvent event) throws NotInitiallizedConfigurationException, IOException {
        if(event.getView().getTitle().equals(ChatColor.AQUA + "스탯")) {
            if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.BARRIER) event.setCancelled(true);
            else if(event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.BARRIER) {
                openMenu((Player) event.getView().getPlayer());
            }
        }
        if(event.getView().getTitle().equals(ChatColor.AQUA + "당신의 수집품")) {
            final String name = event.getView().getPlayer().getName();
            if(event.getCurrentItem() == null) {
                return;
            }
            if(event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + name + "님의 스탯")) {

                    System.out.println(Minevillage_plugin.configuration.initedPlayers);
                    ConfigurationSection section = Minevillage_plugin.configuration.getUserInfo(event.getView().getPlayer().getUniqueId().toString());
                    Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.AQUA + "스탯");
                    inventory.setItem(1, getItemStack(Material.EMERALD, ChatColor.GREEN + "마인마을 코인: " + ChatColor.GOLD + section.getString("coin")));
                    inventory.setItem(2, getItemStack(Material.PAPER, ChatColor.GREEN + "칭호: " + ChatColor.GOLD + Minevillage_plugin.configuration.translatePrefix(section.getString("prefix"))));
                    inventory.setItem(3, getItemStack(Material.OAK_DOOR, ChatColor.GOLD + "내 집 이동"));

                    inventory.setItem(7, getItemStack(Material.BARRIER, ChatColor.GOLD + "뒤로가기"));
                    event.getView().getPlayer().openInventory(inventory);

                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "펫 샵")) {
                    event.getView().getPlayer().sendMessage("펫샵!");
                } else {
                    event.setCancelled(true);
                }
            }
            event.setCancelled(true);
        }
    }

    public void openMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.AQUA + "당신의 수집품");
        inventory.setItem(0, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(1, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(2, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(3, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(4, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(5, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(6, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(7, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(8, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));

        inventory.setItem(9, getItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(17, getItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(10, getItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(16, getItemStack(Material.BLUE_STAINED_GLASS_PANE, ChatColor.RED + "  "));

        inventory.setItem(8, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(11, getSkullStack(p, ChatColor.GREEN + p.getName() + "님의 스탯", null));
        inventory.setItem(12, getItemStack(Material.BONE, ChatColor.GREEN + "펫 샵"));
        inventory.setItem(13, getItemStack(Material.BRICKS, ChatColor.GREEN + "부동산"));
        inventory.setItem(14, getItemStack(Material.QUARTZ_BLOCK, ChatColor.GREEN + "건축자재 가게"));
        inventory.setItem(15, getItemStack(Material.NETHER_BRICKS, ChatColor.GREEN + "인테리어 자재 가게"));

        inventory.setItem(18, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(19, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(20, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(21, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(22, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(23, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(24, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(25, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        inventory.setItem(26, getItemStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + "  "));
        if(p.isOp())
            inventory.setItem(26, getItemStack(Material.ANVIL, ChatColor.GREEN + "모루피하기 시작"));
        p.openInventory(inventory);
    }

    @EventHandler
    public void collectMenu(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
            // testsdfsfsadfhhhhhhhhhgbbdgdasdfasdfdfgdsgasdf
            event.setCancelled(true);
            openMenu(event.getPlayer());
        }
    }

}