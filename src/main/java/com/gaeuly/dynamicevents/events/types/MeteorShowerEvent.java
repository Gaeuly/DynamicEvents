package com.gaeuly.dynamicevents.events.types;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class MeteorShowerEvent implements WorldEvent {

    private final DynamicEvents plugin;
    private final Random random = new Random();

    public MeteorShowerEvent(DynamicEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "MeteorShower";
    }

    @Override
    public void start(Player target) {
        String message = plugin.getConfigManager().getConfig().getString("events.meteor-shower.start-message");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));

        int meteorCount = plugin.getConfigManager().getConfig().getInt("events.meteor-shower.meteor-count", 15);

        new BukkitRunnable() {
            private int meteorsFallen = 0;

            @Override
            public void run() {
                if (meteorsFallen >= meteorCount) {
                    this.cancel();
                    return;
                }
                // Meteor spawn logic
                Location playerLoc = target.getLocation();
                double offsetX = (random.nextDouble() - 0.5) * 100;
                double offsetZ = (random.nextDouble() - 0.5) * 100;
                Location meteorLoc = playerLoc.clone().add(offsetX, 50, offsetZ);

                FallingBlock meteor = target.getWorld().spawnFallingBlock(meteorLoc, Material.MAGMA_BLOCK.createBlockData());
                meteor.setFireTicks(Integer.MAX_VALUE);
                meteor.setHurtEntities(true);
                meteor.setDropItem(false);
                meteor.setCustomName("Meteorite");
                meteor.setVelocity(new Vector(0, -1.5, 0));

                meteorsFallen++;
            }
        }.runTaskTimer(plugin, 60L, 20L);
    }

    @Override
    public void stop() {
        // Logic to stop the event if needed
    }
}