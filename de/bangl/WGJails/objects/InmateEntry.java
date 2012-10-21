/*
 * Copyright (C) 2012 t7seven7t
 *                    BangL <henno.rickowski@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.bangl.WGJails.objects;

import de.bangl.WGJails.Config;
import org.bukkit.Location;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class InmateEntry {

    private boolean jailed = true;
    private long time;
    private String reason;
    private boolean muted;
    private String jailer;
    private transient Location lastLocation;
    private transient long lastActivity = 0L;

    public InmateEntry() { }

    public InmateEntry(long time, String reason, String jailer) {
        this(time, reason, jailer, Config.inmatesMutedUponJail);
    }

    public InmateEntry(long time, String reason, String jailer, boolean muted) {
        this.time = time;
        this.reason = reason;
        this.jailer = jailer;
        this.muted = muted;
    }

    public boolean isJailed() {
        return jailed;
    }

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
