package com.imyvm.onlinemarket;

import cat.nyaa.nyaacore.configuration.ISerializable;
import cat.nyaa.nyaacore.configuration.PluginConfigure;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Configuration extends PluginConfigure {
    private final Main plugin;

    @Serializable
    public String language = "en_US";
    @Serializable
    public boolean marketPlaySound = true;
    @Serializable
    public boolean marketBroadcast = true;
    @Serializable
    public int marketBroadcastCooldown = 120;
    @Serializable
    public int market_tax = 5;
    @Serializable
    public int market_offer_fee = 10;
    @Serializable
    public int market_placement_fee = 1;


    public Map<String, Integer> marketSlot = new HashMap<>();

    public Configuration(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    protected JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void deserialize(ConfigurationSection config) {
        ISerializable.deserialize(config, this);
        marketSlot = new HashMap<>();
        ConfigurationSection slotNumMap = config.getConfigurationSection("marketSlot");
        if (slotNumMap != null) {
            for (String group : slotNumMap.getKeys(false)) {
                marketSlot.put(group, slotNumMap.getInt(group));
            }
        }
    }

    @Override
    public void serialize(ConfigurationSection config) {
        ISerializable.serialize(config, this);
        config.set("marketSlot", null);
        ConfigurationSection slotMap = config.createSection("marketSlot");
        for (String group : marketSlot.keySet()) {
            slotMap.set(group, marketSlot.get(group));
        }
    }
}
