package com.imyvm.onlinemarket.signshop;

/**
 * Created by huang on 2017/8/19.
 */
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ShopInventoryHolder implements InventoryHolder {
    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 54);
    }
}
