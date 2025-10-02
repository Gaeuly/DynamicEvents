package com.gaeuly.dynamicevents.api;

import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called WHEN a WorldEvent has ended.
 */
public class EventEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final WorldEvent worldEvent;
    private final Player lastTarget;

    public EventEndEvent(WorldEvent worldEvent, Player lastTarget) {
        this.worldEvent = worldEvent;
        this.lastTarget = lastTarget;
    }

    /**
     * Gets the instance of the WorldEvent that just ended.
     * @return WorldEvent.
     */
    public WorldEvent getWorldEvent() {
        return worldEvent;
    }
    
    /**
     * Gets the player who was the last target of the event.
     * @return Last target player.
     */
    public Player getLastTarget() {
        return lastTarget;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}