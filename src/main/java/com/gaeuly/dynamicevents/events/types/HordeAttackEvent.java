package com.gaeuly.dynamicevents.events.types;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class HordeAttackEvent implements WorldEvent {

    private final DynamicEvents plugin;
    private final Random random = new Random();
    private BukkitTask task;

    public HordeAttackEvent(DynamicEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "HordeAttack";
    }

    @Override
    public int getDuration() {
        return plugin.getConfigManager().getConfig().getInt("events.horde-attack.duration", 2) * 60;
    }

    @Override
    public void start(Player target) {
        String message = plugin.getConfigManager().getConfig().getString("events.horde-attack.start-message");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));

        int waves = plugin.getConfigManager().getConfig().getInt("events.horde-attack.waves", 3);
        int mobsPerWave = plugin.getConfigManager().getConfig().getInt("events.horde-attack.mobs-per-wave", 10);
        long waveDelay = (getDuration() * 20L) / waves;

        task = new BukkitRunnable() {
            private int currentWave = 0;
            @Override
            public void run() {
                if(currentWave >= waves) {
                    stop();
                    return;
                }
                
                Bukkit.broadcastMessage(ChatColor.RED + "Gelombang " + (currentWave + 1) + " dari " + waves + " telah tiba!");
                
                for(int i = 0; i < mobsPerWave; i++) {
                    Location loc = target.getLocation().add(random.nextInt(20) - 10, 0, random.nextInt(20) - 10);
                    loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                }
                
                currentWave++;
            }
        }.runTaskTimer(plugin, 0L, waveDelay);
    }

    @Override
    public void stop() {
        if(task != null && !task.isCancelled()) {
            task.cancel();
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "[Peristiwa Dunia] Gerombolan monster telah dikalahkan!");
    }
}