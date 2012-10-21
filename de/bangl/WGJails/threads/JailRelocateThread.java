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
package de.bangl.WGJails.threads;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.exceptions.InmatesStillLoadingException;
import de.bangl.WGJails.objects.InmateEntry;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class JailRelocateThread extends SThread {

    @Override
    public void run() {
        try {
            while (WGJailsPlugin.inmates.isLoading())
                Thread.sleep(1000);
            while (WGJailsPlugin.enabled) {
                Thread.sleep(1000);
                if (WGJailsPlugin.jail.isJailSetup()) {
                    for (Entry<String, InmateEntry> entry : WGJailsPlugin.inmates.getInmates().entrySet()) {
                        if (!entry.getValue().isJailed())
                            continue;
                        OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
                        if (p.isOnline()) {
                            if (!WGJailsPlugin.jail.isInside(p.getPlayer().getLocation())) {
                                p.getPlayer().teleport(WGJailsPlugin.jail.getSpawn());
                                WGJailsPlugin.inmatemanager.msg(p.getPlayer(), WGJailsPlugin.lang.getMessage("escape"));
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException | InmatesStillLoadingException e) {}
    }
}
