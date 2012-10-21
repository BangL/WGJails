package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

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
			confirmMessage(SwornJail.lang.getMessage((SwornJail.inmatemanager.toggleMute(target)) ? "mute" : "unmute"),
					target.getName());
		} catch (Exception e) {}
	}

}
