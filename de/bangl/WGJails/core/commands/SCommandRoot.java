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
package de.bangl.WGJails.core.commands;

import de.bangl.WGJails.core.SPlugin;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class SCommandRoot<S extends SPlugin> {

    public Set<SCommand<?>> commands = new HashSet<SCommand<?>>();

    private CmdHelp CMD_HELP = new CmdHelp();

    public SCommandRoot() {
        addCommand(CMD_HELP);
    }

    public void addCommand(SCommand<?> command) {
        commands.add(command);
    }
}
