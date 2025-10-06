package com.gaeuly.dynamicevents.events;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.types.HordeAttackEvent;
import com.gaeuly.dynamicevents.events.types.MeteorShowerEvent;
import com.gaeuly.dynamicevents.events.types.MysteriousEclipseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EventManager {

    private final DynamicEvents plugin;
    private final List<WorldEvent> registeredEvents = new ArrayList<>();
    private final Random random = new Random();
    private long lastEventTimestamp = 0; // (NEW) To track global cooldown

    public EventManager(DynamicEvents plugin) {
        this.plugin = plugin;
        registerEvents();
        startEventTimer();
    }
    
    private void registerEvents() {
        registeredEvents.clear(); // (UPDATED) Clear list on reload
        if (plugin.getConfigManager().getConfig().getBoolean("events.meteor-shower.enabled")) {
            registeredEvents.add(new MeteorShowerEvent(plugin));
        }
        if (plugin.getConfigManager().getConfig().getBoolean("events.horde-attack.enabled")) {
            registeredEvents.add(new HordeAttackEvent(plugin));
        }
        if (plugin.getConfigManager().getConfig().getBoolean("events.mysterious-eclipse.enabled")) { // (NEW)
            registeredEvents.add(new MysteriousEclipseEvent(plugin));
        }
    }

    // (NEW) Called by DynamicEvents.java when '/de reload' is executed
    public void reload() {
        registerEvents();
    }

    private void startEventTimer() {
        long interval = plugin.getConfigManager().getConfig().getLong("check-interval", 10) * 20 * 60;
        new BukkitRunnable() {
            @Override
            public void run() {
                // (NEW) Check cooldown
                long cooldownMillis = plugin.getConfigManager().getConfig().getLong("global-cooldown", 30) * 60 * 1000;
                if (System.currentTimeMillis() - lastEventTimestamp < cooldownMillis && lastEventTimestamp != 0) {
                    return; // Still in cooldown
                }

                if (Bukkit.getOnlinePlayers().isEmpty() || registeredEvents.isEmpty()) {
                    return;
                }
                
                double chance = plugin.getConfigManager().getConfig().getDouble("event-chance", 20.0);
                if (random.nextDouble() * 100 < chance) {
                    triggerRandomEvent();
                }
            }
        }.runTaskTimer(plugin, 0L, interval);
    }
    
    public void triggerRandomEvent() {
        // (NEW) World whitelist logic
        List<String> worldWhitelist = plugin.getConfigManager().getConfig().getStringList("world-whitelist");
        
        List<Player> eligiblePlayers = Bukkit.getOnlinePlayers().stream()
            .filter(p -> worldWhitelist.isEmpty() || worldWhitelist.contains(p.getWorld().getName()))
            .collect(Collectors.toList());

        if (eligiblePlayers.isEmpty()) {
            return; // No players in allowed worlds
        }

        Player target = eligiblePlayers.get(random.nextInt(eligiblePlayers.size()));
        WorldEvent event = registeredEvents.get(random.nextInt(registeredEvents.size()));
        
        startEvent(event, target);
    }
    
    private void startEvent(WorldEvent event, Player target) {
        event.start(target);
        this.lastEventTimestamp = System.currentTimeMillis(); // (NEW) Set timestamp when event starts
    }
    
    public boolean startEventByName(String name, Player target) {
        for (WorldEvent event : registeredEvents) {
            if (event.getName().equalsIgnoreCase(name)) {
                startEvent(event, target); // (UPDATED) Use centralized method
                return true;
            }
        }
        return false;
    }

    public List<WorldEvent> getRegisteredEvents() {
        return registeredEvents;
    }
}