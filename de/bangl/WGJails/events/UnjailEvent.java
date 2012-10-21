package de.bangl.WGJails.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UnjailEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private OfflinePlayer inmate;
	private String jailer;
	
	public UnjailEvent(final OfflinePlayer inmate, final String unjailer) {
		this.inmate = inmate;
		this.jailer = unjailer;
	}
	
	public OfflinePlayer getInmate() {
		return inmate;
	}
	
	public String getUnjailer() {
		return jailer;
	}

	@Override
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
