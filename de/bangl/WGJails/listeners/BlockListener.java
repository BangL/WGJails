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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class BlockListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockDamage(BlockDamageEvent e) {
        if (!canPlayerBuildHere(e.getBlock(), e.getPlayer())) {
            e.setCancelled(true);
        }	
    }

    public boolean canPlayerBuildHere(Block b, Player p) {
        if (!WGJailsPlugin.jail.isInside(b.getLocation())) {
            return true;
        }
        return true;
    }
}
