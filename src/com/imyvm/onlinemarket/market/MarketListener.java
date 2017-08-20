package com.imyvm.onlinemarket.market;

import com.imyvm.onlinemarket.Main;
import com.imyvm.onlinemarket.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketListener implements Listener {
    private final Main plugin;

    public MarketListener(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MarketGUI) {
            event.setCancelled(true);
            if (event.getAction().equals(InventoryAction.PICKUP_ONE) ||
                    event.getAction().equals(InventoryAction.PICKUP_ALL) ||
                    event.getAction().equals(InventoryAction.PICKUP_HALF) ||
                    event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                ((MarketGUI) event.getInventory().getHolder()).onInventoryClick(event);
            } else {
                event.getWhoClicked().closeInventory();
            }
        }
    }
}
