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
package de.bangl.WGJails;

import de.bangl.WGJails.core.util.SPersist;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Config {

    public static boolean inmatesTimeDoesNotCountDownWhileAFK = true;
    public static boolean inmatesMutedUponJail = false;
    public static boolean inmatesCannotPickupItems = true;
    public static boolean inmatesCannotDropItems = true;
    public static boolean blockProtectionEnabled = true;

    public static ArrayList<String> inmatesWhitelistedCommands = new ArrayList<String>();

    static {
        inmatesWhitelistedCommands.add("rules");
        inmatesWhitelistedCommands.add("help");
    }

    public static void load() {
        File f = new File(WGJailsPlugin.p.getDataFolder(), "config.txt");
        /**
         * For backwards compatibility check that the old plugin config exists and copy
         * any settings from the old one over to the new config.
         */
        if (!f.exists()) {
            f = new File(WGJailsPlugin.p.getDataFolder(), "config.yml");
            if (f.exists()) {
                WGJailsPlugin.log("Old config file found, copying old settings into new config.");
                SPersist.load(null, Config.class, "config.txt");
                Config.inmatesTimeDoesNotCountDownWhileAFK = WGJailsPlugin.p.getConfig().getBoolean("jailtime-counts-down-while-afk", true);
                Config.inmatesMutedUponJail = WGJailsPlugin.p.getConfig().getBoolean("mute-inmates-on-jail", false);
                for (String s : WGJailsPlugin.p.getConfig().getStringList("whitelisted-commands").toArray(new String[0])) {
                    Config.inmatesWhitelistedCommands.add(s);
                }
                SPersist.save(null, Config.class, "config.txt");
                return;
            }
        }
        SPersist.load(null, Config.class, "config.txt");
    }
}
