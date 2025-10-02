package com.gaeuly.dynamicevents.listeners;

import com.gaeuly.dynamicevents.DynamicEvents;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.Random;

public class MeteorListener implements Listener {

    private final DynamicEvents plugin;
    private final Random random = new Random();

    public MeteorListener(DynamicEvents plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMeteorLand(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.FALLING_BLOCK && "Meteorite".equals(event.getEntity().getCustomName())) {
            event.setCancelled(true);
            event.getEntity().remove();

            Block block = event.getBlock();
            Location loc = block.getLocation();
            loc.getWorld().createExplosion(loc, 2.0f, false, false);

            // Determine loot from config
            ConfigurationSection lootConfig = plugin.getConfigManager().getConfig().getConfigurationSection("events.meteor-shower.loot");
            double roll = random.nextDouble() * 100;
            double cumulativeChance = 0;

            for (String key : lootConfig.getKeys(false)) {
                cumulativeChance += lootConfig.getDouble(key);
                if (roll < cumulativeChance) {
                    block.setType(Material.valueOf(key));
                    return;
                }
            }
        }
    }
}