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

import de.bangl.WGJails.Config;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.threads.InmateCounterThread;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (WGJailsPlugin.inmatemanager.inJailList(e.getPlayer())) {
            if (!WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).isJailed()) {
                WGJailsPlugin.inmatemanager.release(e.getPlayer());
            } else {
                new InmateCounterThread(e.getPlayer());
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer())) {
            e.setCancelled(true);
        } 
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerDropItem(PlayerDropItemEvent e) {	
        if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer()) && Config.inmatesCannotDropItems) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + WGJailsPlugin.lang.getMessage("cannotdropitems"));
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (WGJailsPlugin.inmatemanager.isJailed(p)) {
            if (WGJailsPlugin.inmates.getInmate(p.getName()).isMuted()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + WGJailsPlugin.lang.getMessage("muted"));
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (WGJailsPlugin.inmatemanager.isJailed(player)) {
            if ((!WGJailsPlugin.hasWGCommandFlags
                    || !de.bangl.wgcf.Utils.cmdAllowedAtLocation(WGJailsPlugin.pluginWorldGuard, event.getMessage().substring(1), player.getLocation()))
                    && !event.getMessage().equalsIgnoreCase("/jailstatus")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + WGJailsPlugin.lang.getMessage("cannotUseCommands"));
            }
        }
    }
}