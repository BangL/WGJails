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
package de.bangl.WGJails.core;

import de.bangl.WGJails.core.commands.CmdHelp;
import de.bangl.WGJails.core.commands.SCommand;
import de.bangl.WGJails.core.commands.SCommandRoot;
import de.bangl.WGJails.core.threads.ReloadThread;
import de.bangl.WGJails.core.util.SLang;
import de.bangl.WGJails.core.util.Util;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public abstract class SPlugin extends JavaPlugin implements ISPlugin {
    private static String PLUGIN_NAME;
    private static ArrayList<String> COMMAND_PREFIXES = new ArrayList<String>();
    private static ArrayList<String> enabledSoftDependPlugins = new ArrayList<String>();

    public static SPlugin p;
    public static SLang lang;
    public static SCommandRoot<?> commandRoot;
    public volatile static boolean enabled;

    public boolean preEnable() {
        p = this;
        PLUGIN_NAME = this.getName();

        if (this.getDescription().getDepend() != null) {
            for (String s : this.getDescription().getDepend()) {
                if (!Bukkit.getPluginManager().isPluginEnabled(s)) {
                    log(s + " not found. Disabling " + PLUGIN_NAME + ".");
                    return false;
                }
            }
        }

        if (this.getDescription().getSoftDepend() != null) {
            for (String s : this.getDescription().getSoftDepend()) {
                if (!Bukkit.getPluginManager().isPluginEnabled(s)) {
                    log(s + " not found. Disabling " + s + " related features.");
                } else {
                    log(s + " was found! Enabling " + s + " related features.");
                    enabledSoftDependPlugins.add(s);
                }
            }
        }

        if (this.getDescription().getCommands().size() == 1) {
            for (Entry<String, Map<String, Object>> entry : this.getDescription().getCommands().entrySet()) {
                COMMAND_PREFIXES.add(entry.getKey());
            }
        }

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        enabled = true;
        return true;
    }

    @Override
    public void onEnable() {
        preEnable();
        lang = new SLang();
        SLang.load();
        commandRoot = new SCommandRoot<SPlugin>();
    }

    public void preDisable() {
        enabled = false;
    }

    @Override
    public void onDisable() {
        preDisable();
    }

    public void reload() {
        new ReloadThread();
    }

    public void afterReload() { }

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {		
        String cmdlbl;
        if (getCommandPrefix() == null) {
            cmdlbl = cmd.getName();
        } else {
            cmdlbl = args[0];
        }

        for (SCommand<?> command : commandRoot.commands) {	
            if (cmdlbl.equalsIgnoreCase(command.getName()) || command.getAliases().contains(cmdlbl.toLowerCase())) {
                ArrayList<String> argList = new ArrayList<String>();
                for (int i = 0; i < args.length; i++) {
                    argList.add(args[i]);
                }

                args = argList.toArray(new String[0]);				

                command.execute(sender, args);
                return true;
            }
        }

        new CmdHelp().execute(sender, args);
        return true;
    }

    public static String getCommandPrefix() {return (!COMMAND_PREFIXES.isEmpty()) ? COMMAND_PREFIXES.get(0) : null;}	
    public static boolean isPluginEnabled(String s) {return enabledSoftDependPlugins.contains(s);}

    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    public static void log(Level level, String msg) {
        Bukkit.getLogger().log(level, "[" + PLUGIN_NAME + "] " + msg);
    }

    public static void log(String msg, Object... args) {
        log(Level.INFO, Util.parseMsg(msg, args));
    }
}
