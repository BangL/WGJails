package com.minesworn.swornjail.commands;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

public class CmdStatus extends SJCommand {

	public CmdStatus() {
		this.name = "jailstatus";
		this.description = "Check your jail status.";
		this.permission = Permission.STATUS.node;
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (!SwornJail.inmatemanager.isJailed(player)) {
			errorMessage(SwornJail.lang.getErrorMessage("notjailedself"));
			return;
		}
		
		confirmMessage(SwornJail.inmatemanager.getJailString(player, true));
	}
}
