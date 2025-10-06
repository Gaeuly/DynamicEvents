package com.gaeuly.dynamicevents;

import com.gaeuly.dynamicevents.commands.MainCommand;
import com.gaeuly.dynamicevents.events.EventManager;
import com.gaeuly.dynamicevents.listeners.MeteorListener;
import com.gaeuly.dynamicevents.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicEvents extends JavaPlugin {

    private static DynamicEvents instance;
    private ConfigManager configManager;
    private EventManager eventManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.eventManager = new EventManager(this);

        getServer().getPluginManager().registerEvents(new MeteorListener(this), this);

        getCommand("dynamicevent").setExecutor(new MainCommand(this));
        getCommand("dynamicevent").setTabCompleter(new MainCommand(this));
        
        getLogger().info("DynamicEventsPro v1.1.0 has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DynamicEventsPro has been disabled!");
    }

    // (NEW) This method is called by MainCommand when '/de reload' is executed
    public void reloadPlugin() {
        getConfigManager().loadConfigs();
        getEventManager().reload();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
    
    public static DynamicEvents getInstance() {
        return instance;
    }
}