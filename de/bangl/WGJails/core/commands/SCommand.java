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
import de.bangl.WGJails.core.permissions.PermissionBase;
import de.bangl.WGJails.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public abstract class SCommand<S extends SPlugin> implements ISCommand {

    public CommandSender sender;
    public Player player;
    public String args[];
    public boolean isPlayer = false;
    public boolean mustBePlayer = false;

    public String permission = new String();
    public String name = new String();
    public String description = new String();

    public List<String> requiredArgs = new ArrayList<String>(2);
    public List<String> optionalArgs = new ArrayList<String>(2);
    public List<String> aliases = new ArrayList<String>(2);

    public void execute(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        if (sender instanceof Player) {
            isPlayer = true;
            player = (Player) sender;
        }

        if (mustBePlayer && !isPlayer) {
            errorMessage(SPlugin.lang.getErrorMessage("mustbeplayer"));
            return;
        }

        if (requiredArgs.size() > args.length) {
            errorMessage(SPlugin.lang.getErrorMessage("argcount") + getUsageTemplate(false));
            return;
        }

        if (hasPermission()) {
            perform();
        } else {
            errorMessage(SPlugin.lang.getErrorMessage("permission"));
        }
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public boolean hasPermission() {
        return (this.permission == null) ? true : PermissionBase.hasPermission(sender, permission);
    }

    public void msg(String msg) {
        sender.sendMessage(msg);
    }

    public void errorMessage(String msg, Object... args) {
        sender.sendMessage(ChatColor.RED + "Error: "+ Util.parseMsg(msg, args));
    }

    public void confirmMessage(String msg, Object... args) {
        sender.sendMessage(ChatColor.YELLOW + Util.parseMsg(msg, args));
    }

    public String getUsageTemplate(boolean help) {
        StringBuilder ret = new StringBuilder();
        ret.append(ChatColor.RED + "/");

        if (SPlugin.getCommandPrefix() != null) {
            ret.append(SPlugin.getCommandPrefix() + " ");
        }

        ret.append( name);

        if (aliases.size() != 0) {
            ret.append(ChatColor.GOLD + "(");
            for (int i = 0; i < aliases.size(); i++) {
                ret.append(aliases.get(i) + ((i == aliases.size() - 1) ? "" : ", "));
            }
            ret.append(")");
        }

        ret.append(ChatColor.DARK_RED + " ");

        for (String s : requiredArgs) {
            ret.append("<" + s + "> ");
        }

        for (String s : optionalArgs) {
            ret.append("[" + s + "] ");
        }

        if (help) {
            ret.append(ChatColor.YELLOW + description);
        }

        return ret.toString();
    }
}
