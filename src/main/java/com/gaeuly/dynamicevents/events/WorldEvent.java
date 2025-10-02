package com.gaeuly.dynamicevents.events;

import org.bukkit.entity.Player;

// This interface ensures all event classes have the same methods.
public interface WorldEvent {

    /**
     * Gets the unique name of the event.
     * @return Event name.
     */
    String getName();

    /**
     * Logic to start the event.
     * @param target The player who is the center of the event.
     */
    void start(Player target);

    /**
     * Logic to forcibly stop the event.
     */
    void stop();
}