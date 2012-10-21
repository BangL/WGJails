package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

public class CmdCheck extends SJCommand {

	public CmdCheck() {
		this.name = "jailcheck";
		this.description = "Checks a player's jail status.";
		this.permission = Permission.CHECK.node;
		this.requiredArgs.add("player");
	}
	
	@Override
	public void perform() {		
		OfflinePlayer target;
		try {
			target = getTarget(args[0], true);
		} catch (Exception e) {
			return;
		}
		
		confirmMessage(SwornJail.inmatemanager.getJailString(target, false));
	}
	
}
