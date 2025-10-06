package com.gaeuly.dynamicevents.events.types;

import com.gaeuly.dynamicevents.DynamicEvents;
import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class MysteriousEclipseEvent implements WorldEvent {

    private final DynamicEvents plugin;
    private BukkitTask task;
    private BossBar bossBar;

    public MysteriousEclipseEvent(DynamicEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "MysteriousEclipse";
    }

    @Override
    public int getDuration() {
        return plugin.getConfigManager().getConfig().getInt("events.mysterious-eclipse.duration", 3) * 60;
    }

    @Override
    public void start(Player target) {
        String message = plugin.getConfigManager().getConfig().getString("events.mysterious-eclipse.start-message");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        
        bossBar = Bukkit.createBossBar(
                ChatColor.translateAlternateColorCodes('&', "&5&lGerhana Misterius"),
                BarColor.PURPLE,
                BarStyle.SOLID
        );
        bossBar.setVisible(true);

        int duration = getDuration();
        final long startTime = System.currentTimeMillis();

        task = new BukkitRunnable() {
            @Override
            public void run() {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                double progress = 1.0 - ((double) elapsedTime / duration);
                
                if (progress <= 0) {
                    stop();
                    return;
                }
                
                bossBar.setProgress(Math.max(0, progress));

                for (Player p : Bukkit.getOnlinePlayers()) {
                    bossBar.addPlayer(p);
                    if(!p.hasPotionEffect(PotionEffectType.DARKNESS)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, (duration + 5) * 20, 0, false, false));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
        }
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.removePotionEffect(PotionEffectType.DARKNESS);
        }
        
        List<String> effects = plugin.getConfigManager().getConfig().getStringList("events.mysterious-eclipse.end-effects");
        for(String effectString : effects) {
            try {
                String[] parts = effectString.split(":");
                PotionEffectType type = PotionEffectType.getByName(parts[0].toUpperCase());
                int level = Integer.parseInt(parts[1]) - 1;
                int durationTicks = Integer.parseInt(parts[2]) * 20;
                
                if(type != null) {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.addPotionEffect(new PotionEffect(type, durationTicks, level));
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Format efek di config.yml salah: " + effectString);
            }
        }
        Bukkit.broadcastMessage(ChatColor.GOLD + "[Peristiwa Dunia] " + ChatColor.AQUA + "Matahari telah kembali, memberkati semua pemain!");
    }
}