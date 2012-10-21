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
package de.bangl.WGJails.commands;

import de.bangl.WGJails.PermissionsManager.Permission;
import de.bangl.WGJails.WGJailsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class CmdSet extends SJCommand {

    public CmdSet() {
        this.name = "jailset";
        this.description = "Set the region of jail";
        this.permission = Permission.SET.node;
        this.optionalArgs.add("cancel");
        this.mustBePlayer = true;
    }

    @Override
    public void perform() {
        if (args.length == 1) {
            WGJailsPlugin.jail.resetJailStage();
            confirmMessage(WGJailsPlugin.lang.getMessage("setcancelled"));
            return;
        }

        Location l = player.getLocation();

        switch (WGJailsPlugin.jail.getJailStage()) {
        case 0: 
            confirmMessage(WGJailsPlugin.lang.getMessage("jailset1"));
            break;
        case 1: 
            WGJailsPlugin.jail.setWorld(l.getWorld());
            WGJailsPlugin.jail.setMin(l.toVector().toBlockVector());
            msg(ChatColor.RED + "Corner 1 set!");
            confirmMessage(WGJailsPlugin.lang.getMessage("jailset2"));
            break;
        case 2: 
            WGJailsPlugin.jail.setMax(l.toVector().toBlockVector());
            msg(ChatColor.RED + "Corner 2 set!");
            confirmMessage(WGJailsPlugin.lang.getMessage("jailset3"));
            break;
        case 3: 
            WGJailsPlugin.jail.setSpawn(l);
            msg(ChatColor.RED + "Jail spawn set!");
            confirmMessage(WGJailsPlugin.lang.getMessage("jailset4"));
            break;
        case 4: 
            WGJailsPlugin.jail.setExit(l);
            msg(ChatColor.RED + "Jail exit set!");
            confirmMessage(WGJailsPlugin.lang.getMessage("jailset5"));
            break;
        default: 
            break;
        }
        WGJailsPlugin.jail.nextJailStage();
    }
}
