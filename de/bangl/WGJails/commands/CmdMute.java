package de.bangl.WGJails.commands;

import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

public class CmdMute extends SJCommand {

	public CmdMute() {
		this.name = "jailmute";
		this.description = "Mutes a jailed player.";
		this.permission = Permission.MUTE.node;
		this.requiredArgs.add("player");
	}
	
	@Override
	public void perform() {
		try {
			OfflinePlayer target = getTarget(args[0], true);
			confirmMessage(WGJailsPlugin.lang.getMessage((WGJailsPlugin.inmatemanager.toggleMute(target)) ? "mute" : "unmute"),
					target.getName());
		} catch (Exception e) {}
	}

}
