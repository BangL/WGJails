package de.bangl.WGJails.objects;

import org.bukkit.Location;

import de.bangl.WGJails.Config;

public class InmateEntry {

	private boolean jailed = true;
	private long time;
	private String reason;
	private boolean muted;
	private String jailer;
	private transient Location lastLocation;
	private transient long lastActivity = 0L;
	
	public InmateEntry() {
		
	}
	
	public InmateEntry(long time, String reason, String jailer) {
		this(time, reason, jailer, Config.inmatesMutedUponJail);
	}
	
	public InmateEntry(long time, String reason, String jailer, boolean muted) {
		this.time = time;
		this.reason = reason;
		this.jailer = jailer;
		this.muted = muted;
	}
	
	public boolean isJailed() {return jailed;}

	public void unjail() {
		jailed = false;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public String getJailer() {
		return jailer;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public long getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(long lastActivity) {
		this.lastActivity = lastActivity;
	}
	
}
