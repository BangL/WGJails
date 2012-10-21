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

import de.bangl.WGJails.Config;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.objects.InmateEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class InmateCounterThread extends SThread {
    final Player p;
    public InmateCounterThread(final Player p) {
        super();
        this.p = p;
    }

    @Override
    public void run() {
        try{
            while (WGJailsPlugin.inmates.isLoading()) {
                Thread.sleep(1000);
            }
            InmateEntry entry = WGJailsPlugin.inmates.getInmate(p.getName());
            long lastSpam = 0;

            while (WGJailsPlugin.enabled && entry.isJailed() && p.isOnline()) {
                Thread.sleep(1000);
                long now = System.currentTimeMillis();
                if (Config.inmatesTimeDoesNotCountDownWhileAFK && now - entry.getLastActivity() > 30000L && now - lastSpam > 20000L) {
                    lastSpam = now;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
                        @Override
                        public void run() {
                            p.sendMessage(ChatColor.RED + WGJailsPlugin.lang.getMessage("afk"));
                        }
                    });
                } else {
                    if (entry.getTime() < 0) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
                            @Override
                            public void run() {
                                WGJailsPlugin.inmatemanager.unjail(p, null);
                            }
                        });
                        WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logfinishsentence"), p.getName());
                    } else {
                        entry.setTime(entry.getTime() - 1000);
                    }
                }
            }
        } catch (InterruptedException e) { }
    }
}
