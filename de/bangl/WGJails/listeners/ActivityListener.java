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
package de.bangl.WGJails.listeners;

import de.bangl.WGJails.WGJailsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class ActivityListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer())) {
            WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
            @Override
            public void run() {
                if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer())) {
                    WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
                }
            }
        });
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer())) {
            WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
        }
    }
}
