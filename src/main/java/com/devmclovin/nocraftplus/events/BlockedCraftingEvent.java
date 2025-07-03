package net.corruptmc.nocraftplus.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class BlockedCraftingEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private PrepareItemCraftEvent craftEvent;
    private boolean cancelled;

    //Event for when a item crafting is blocked.
    public BlockedCraftingEvent(PrepareItemCraftEvent craftEvent)
    {
        this.craftEvent = craftEvent;
    }

    public PrepareItemCraftEvent getCraftEvent()
    {
        return this.craftEvent;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
