package com.imyvm.onlinemarket;

import com.imyvm.onlinemarket.market.MarketGUI;
import com.imyvm.onlinemarket.market.MarketManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Events implements Listener {
    private final Main plugin;

    public Events(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MarketGUI) {
            return;
        }
        if (event.getCurrentItem() != null && MarketManager.isMarketItem(event.getCurrentItem())) {
            event.setCurrentItem(new ItemStack(Material.AIR));
        } else if (event.getCursor() != null && MarketManager.isMarketItem(event.getCursor())) {
            event.setCursor(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        List<ItemStack> items = plugin.database.getTemporaryStorage(ev.getPlayer());
        if (items.size() > 0) {
            ev.getPlayer().sendMessage(I18n.format("user.info.has_temporary_storage"));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getItemDrop() != null && MarketManager.isMarketItem(e.getItemDrop().getItemStack())) {
            e.getItemDrop().setItemStack(new ItemStack(Material.AIR));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickupItem(EntityPickupItemEvent e) {
        if (e.getItem() != null && MarketManager.isMarketItem(e.getItem().getItemStack())) {
            e.getItem().setItemStack(new ItemStack(Material.AIR));
            e.setCancelled(true);
        }
    }
}
