/*
 * Copyright (C) 2012 BangL <henno.rickowski@googlemail.com>
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
package de.bangl.WGJails;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Utils {

    public static WGCustomFlagsPlugin getWGCustomFlags(WGJailsPlugin plugin) {
        final Plugin wgcfp = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
        if (wgcfp == null || !(wgcfp instanceof WGCustomFlagsPlugin)) {
            return null;
        }
        return (WGCustomFlagsPlugin)wgcfp;
    }

    public static WorldGuardPlugin getWorldGuard(WGJailsPlugin plugin) {
        final Plugin wgp = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wgp == null || !(wgp instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)wgp;
    }
}
