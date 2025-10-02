package com.gaeuly.dynamicevents.events;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.types.HordeAttackEvent;
import com.gaeuly.dynamicevents.events.types.MeteorShowerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {

    private final DynamicEvents plugin;
    private final List<WorldEvent> registeredEvents = new ArrayList<>();
    private final Random random = new Random();

    public EventManager(DynamicEvents plugin) {
        this.plugin = plugin;
        registerEvents();
        startEventTimer();
    }
    
    private void registerEvents() {
        // Register all event types here
        if (plugin.getConfigManager().getConfig().getBoolean("events.meteor-shower.enabled")) {
            registeredEvents.add(new MeteorShowerEvent(plugin));
        }
        if (plugin.getConfigManager().getConfig().getBoolean("events.horde-attack.enabled")) {
            registeredEvents.add(new HordeAttackEvent(plugin));
        }
    }

    private void startEventTimer() {
        long interval = plugin.getConfigManager().getConfig().getLong("check-interval", 10) * 20 * 60;
        new BukkitRunnable() {
            @Override
            public void run() {
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
        // Select a random event from the registered events
        WorldEvent event = registeredEvents.get(random.nextInt(registeredEvents.size()));
        
        // Select a random player as the target
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        Player target = players[random.nextInt(players.length)];
        
        // Start the event
        event.start(target);
    }
    
    // Method to start an event manually via command
    public boolean startEventByName(String name, Player target) {
        for (WorldEvent event : registeredEvents) {
            if (event.getName().equalsIgnoreCase(name)) {
                event.start(target);
                return true;
            }
        }
        return false;
    }
}