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
import de.bangl.WGJails.core.commands.SCommandRoot;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class SJCommandRoot extends SCommandRoot<WGJailsPlugin> {
    private CmdCheck CMD_CHECK = new CmdCheck();
    private CmdJail CMD_JAIL = new CmdJail();
    private CmdMute CMD_MUTE = new CmdMute();
    private CmdSet CMD_SET = new CmdSet();
    private CmdStatus CMD_STATUS = new CmdStatus();
    private CmdUnjail CMD_UNJAIL = new CmdUnjail();

    public SJCommandRoot() {
        super();
        this.addCommand(CMD_CHECK);
        this.addCommand(CMD_JAIL);
        this.addCommand(CMD_MUTE);
        this.addCommand(CMD_SET);
        this.addCommand(CMD_STATUS);
        this.addCommand(CMD_UNJAIL);
    }
}
