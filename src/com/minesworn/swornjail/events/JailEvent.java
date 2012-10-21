package com.minesworn.swornjail.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minesworn.swornjail.objects.InmateEntry;

public class JailEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private OfflinePlayer inmate;
	private InmateEntry entry;
	
	public JailEvent(final OfflinePlayer inmate, final InmateEntry entry) {
		this.inmate = inmate;
		this.entry = entry;
	}
	
	public OfflinePlayer getInmate() {
		return inmate;
	}
	
	public InmateEntry getInmateEntry() {
		return entry;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
