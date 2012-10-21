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
package de.bangl.WGJails.lang;

import de.bangl.WGJails.core.util.SLang;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Lang extends SLang {	
    static {
        messages.add("escape: DO NOT TRY TO ESCAPE SWORNJAIL");
        messages.add("afk: Your jail time will not count down while afk.");

        messages.add("unjail: You have been unjailed. Remember to read the rules.");
        messages.add("jail: You have been jailed for %s by %s.");

        messages.add("jailreason: %s is jailed for %s, %s by %s.");
        messages.add("jailstatus: %s have been jailed for %s, %s by %s.");

        messages.add("logjail: %s was jailed by %s for %s");
        messages.add("logunjail: %s was unjailed by %s");
        messages.add("logjailreason: %s's jail reason was changed to %s by %s");
        messages.add("logjailtime: %s's jail time was changed to %s by %s");
        messages.add("logreload: %s reloaded SwornJail.");
        messages.add("logfinishsentence: %s finished serving their sentence.");

        messages.add("confirmjail: %s is now jailed.");
        messages.add("confirmunjail: %s is now unjailed.");
        messages.add("confirmjailreason: %s's jail reason was changed.");
        messages.add("confirmjailtime: %s's jail time was changed.");
        messages.add("confirmreload: SwornJail reloaded!");

        messages.add("mute: %s is now muted.");
        messages.add("unmute: %s is now unmuted.");
        messages.add("setcancelled: Setting of region was cancelled.");

        messages.add("cannotdropitems: You cannot drop items while jailed.");
        messages.add("muted: You can't talk while muted.");
        messages.add("cannotusecommands: You cannot use commands while in jail.");

        messages.add("jailset1: Please stand in one corner of the jail and use /jailset.");
        messages.add("jailset2: Please stand in the opposite corner of the jail and use /jailset.");
        messages.add("jailset3: Now stand where you want inmates to warp to and use /jailset.");
        messages.add("jailset4: Finally stand where you want users to teleport when they are released and use /jailset.");
        messages.add("jailset5: Jail successfully created!");

        errorMessages.add("doesnotexist: %s doesn't exist!");
        errorMessages.add("notjailed: %s is not in jail.");
        errorMessages.add("notjailedself: You are not in jail.");
        errorMessages.add("alreadyjailed: %s is already in jail.");
        errorMessages.add("eventcancelled: Your command was cancelled by another plugin.");
        errorMessages.add("jailnotsetup: Jail points are not configured correctly, user /jailset to get started.");
        errorMessages.add("timeformat: Time format is wrong.");
        errorMessages.add("outoftimerange: Invalid time. Please set a positive time for this inmate.");
    }
}
