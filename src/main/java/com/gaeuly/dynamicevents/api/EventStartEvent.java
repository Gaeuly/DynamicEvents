package com.gaeuly.dynamicevents.api;

import com.gaeuly.dynamicevents.events.WorldEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called RIGHT BEFORE a WorldEvent starts.
 * This event is cancellable.
 */
public class EventStartEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final WorldEvent worldEvent;
    private final Player target;
    private boolean isCancelled;

    public EventStartEvent(WorldEvent worldEvent, Player target) {
        this.worldEvent = worldEvent;
        this.target = target;
        this.isCancelled = false;
    }

    /**
     * Gets the instance of the WorldEvent that is about to start.
     * @return WorldEvent.
     */
    public WorldEvent getWorldEvent() {
        return worldEvent;
    }
    
    /**
     * Gets the player who is the target of the event.
     * @return Target player.
     */
    public Player getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}