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

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.core.commands.SCommand;
import de.bangl.WGJails.core.util.Util;
import de.bangl.WGJails.exceptions.PlayerNotFoundException;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public abstract class SJCommand extends SCommand<WGJailsPlugin> {

    public OfflinePlayer getTarget(String name, boolean shouldBeJailed) throws PlayerNotFoundException, Exception {
        OfflinePlayer target = Util.matchOfflinePlayer(name);
        if (target == null) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage("doesnotexist"), name);
            throw new PlayerNotFoundException();
        }
        if (WGJailsPlugin.inmatemanager.isJailed(target) != shouldBeJailed) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage((shouldBeJailed) ? "notjailed" : "alreadyjailed"), target.getName());
            throw new Exception();
        }
        return target;
    }
}
