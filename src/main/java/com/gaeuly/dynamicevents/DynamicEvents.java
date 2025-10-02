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

        // 1. Load configuration files
        this.configManager = new ConfigManager(this);

        // 2. Initialize the event manager
        this.eventManager = new EventManager(this);

        // 3. Register event listeners
        getServer().getPluginManager().registerEvents(new MeteorListener(this), this);

        // 4. Register command executor and tab completer
        getCommand("dynamicevent").setExecutor(new MainCommand(this));
        getCommand("dynamicevent").setTabCompleter(new MainCommand(this));

        getLogger().info("DynamicEventsPro has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DynamicEventsPro has been disabled!");
    }

    // Getter methods to allow access from other classes
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