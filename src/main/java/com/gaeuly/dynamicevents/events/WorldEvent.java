package com.gaeuly.dynamicevents.events;

import org.bukkit.entity.Player;

public interface WorldEvent {

    String getName();

    /**
     * (BARU) Mendapatkan durasi event dalam detik.
     * @return Durasi dalam detik.
     */
    int getDuration();

    void start(Player target);

    void stop();
}