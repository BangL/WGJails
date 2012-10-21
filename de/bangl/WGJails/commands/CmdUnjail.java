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
import org.bukkit.OfflinePlayer;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class CmdUnjail extends SJCommand {

    public CmdUnjail() {
        this.name = "unjail";
        this.description = "Unjail players";
        this.permission = Permission.UNJAIL.node;
        this.requiredArgs.add("player");
    }

    @Override
    public void perform() {
        OfflinePlayer target;
        try {
            target = getTarget(args[0], true);
        } catch (Exception e) {
            return;
        }

        if (!WGJailsPlugin.inmatemanager.unjail(target, sender.getName())) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage("eventcancelled"));
            return;
        }

        WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logunjail"), target.getName(), sender.getName());
        confirmMessage(WGJailsPlugin.lang.getMessage("confirmunjail"), target.getName());
    }
}
