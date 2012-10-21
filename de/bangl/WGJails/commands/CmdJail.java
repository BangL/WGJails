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
import de.bangl.WGJails.core.util.Util;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class CmdJail extends SJCommand {

    public CmdJail() {
        this.name = "jail";
        this.description = "Jail players";
        this.permission = Permission.JAIL.node;
        this.requiredArgs.add("player");
        this.requiredArgs.add("time");
        this.requiredArgs.add("reason");
    }

    @Override
    public void perform() {
        if (!WGJailsPlugin.jail.isJailSetup()) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage("jailnotsetup"));
            return;
        }

        OfflinePlayer target;
        long time;
        StringBuilder reason = new StringBuilder();
        try {
            target = getTarget(args[0], false);
            time = Util.parseTime(args[1]);
        } catch (Exception e) {
            if (e != null && e.getMessage() != null)
                if (e.getMessage().equals("badtime"))
                errorMessage(WGJailsPlugin.lang.getErrorMessage("timeformat"));
            return;
        }

        if (time < 1000) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage("outoftimerange"));
            return;
        }

        for (int i = 2; i < args.length; i++) {
            reason.append(args[i] + " ");
        }

        if (reason.lastIndexOf(" ") != -1) {
            reason.deleteCharAt(reason.lastIndexOf(" "));
        }

        if (!WGJailsPlugin.inmatemanager.jail(target, time, reason.toString(), sender.getName())) {
            errorMessage(WGJailsPlugin.lang.getErrorMessage("eventcancelled"));
            return;
        }
        WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logjail"), target.getName(), sender.getName(), reason.toString());
        confirmMessage(WGJailsPlugin.lang.getMessage("confirmjail"), target.getName());
    }
}
