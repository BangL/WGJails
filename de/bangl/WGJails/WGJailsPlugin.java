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

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import de.bangl.WGJails.commands.SJCommandRoot;
import de.bangl.WGJails.core.SPlugin;
import de.bangl.WGJails.lang.Lang;
import de.bangl.WGJails.listeners.ActivityListener;
import de.bangl.WGJails.listeners.BlockListener;
import de.bangl.WGJails.listeners.PlayerListener;
import de.bangl.WGJails.objects.SimpleVector;
import de.bangl.WGJails.threads.InmateCounterThread;
import de.bangl.WGJails.threads.JailRelocateThread;
import de.bangl.WGJails.threads.SaveThread;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class WGJailsPlugin extends SPlugin {
    public static Jail jail;
    public static Inmates inmates;
    public static InmateManager inmatemanager;
    public static boolean hasWGCommandFlags;
    public static WGCustomFlagsPlugin pluginWGCustomFlags;
    public static WorldGuardPlugin pluginWorldGuard;

    @Override
    public void onEnable() {
        preEnable();
        ConfigurationSerialization.registerClass(SimpleVector.class);

        // Required dependencies
        pluginWorldGuard = Utils.getWorldGuard(this);
        pluginWGCustomFlags = Utils.getWGCustomFlags(this);

        lang = new Lang();
        Lang.load();
        Config.load();
        log("Config loaded!");
        jail = new Jail();
        log("Jail loaded!");
        inmates = new Inmates();	
        inmatemanager = new InmateManager();
        commandRoot = new SJCommandRoot();

        // Check for optional dependencies
        hasWGCommandFlags = this.getServer().getPluginManager().getPlugin("WGCommandFlags") != null;

        registerEvents();

        new SaveThread();
        new JailRelocateThread();

    }

    @Override
    public void onDisable() {
        preDisable();
        jail.save();
        inmates.save();
    }

    @Override
    public void afterReload() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (inmatemanager.isJailed(p)) {
                new InmateCounterThread(p);
            }
        }
    }

    public void registerEvents() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().registerEvents(new ActivityListener(), WGJailsPlugin.p);
                Bukkit.getPluginManager().registerEvents(new PlayerListener(), WGJailsPlugin.p);	
                if (Config.blockProtectionEnabled)
                    Bukkit.getPluginManager().registerEvents(new BlockListener(), WGJailsPlugin.p);
            }
        });
    }

    public WorldGuardPlugin getWGP() {
        return pluginWorldGuard;
    }

    public WGCustomFlagsPlugin getWGCFP() {
        return pluginWGCustomFlags;
    }

    public Boolean hasWGCommandFlags() {
        return hasWGCommandFlags;
    }
}
