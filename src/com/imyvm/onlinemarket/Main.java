package com.imyvm.onlinemarket;

import com.imyvm.onlinemarket.market.MarketListener;
import com.imyvm.onlinemarket.market.MarketManager;
import com.imyvm.onlinemarket.utils.EconomyUtil;
import com.imyvm.onlinemarket.utils.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static Main instance;
    public Logger logger;
    public Configuration config;
    public EconomyUtil eco;
    public Database database;
    public Events eventHandler;
    public CommandHandler commandHandler;
    public MarketManager marketManager;
    public I18n i18n;
    public MarketListener marketListener;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        config = new Configuration(this);
        config.load();
        i18n = new I18n(this, this.config.language);
        commandHandler = new CommandHandler(this, this.i18n);
        getCommand("onlinemarket").setExecutor(commandHandler);
        getCommand("onlinemarket").setTabCompleter(commandHandler);
        database = new Database(this);
        eco = new EconomyUtil(this);
        marketManager = new MarketManager(this);
        marketListener = new MarketListener(this);
        eventHandler = new Events(this);
    }

    @Override
    public void onDisable() {
        config.save();
    }

    public void reload() {
        marketManager.closeAllGUI();
        marketManager.cancel();
        config.load();
        i18n.load();
        marketManager = new MarketManager(this);
    }
}




