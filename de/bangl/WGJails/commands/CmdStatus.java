package de.bangl.WGJails.commands;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

public class CmdStatus extends SJCommand {

	public CmdStatus() {
		this.name = "jailstatus";
		this.description = "Check your jail status.";
		this.permission = Permission.STATUS.node;
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (!WGJailsPlugin.inmatemanager.isJailed(player)) {
			errorMessage(WGJailsPlugin.lang.getErrorMessage("notjailedself"));
			return;
		}
		
		confirmMessage(WGJailsPlugin.inmatemanager.getJailString(player, true));
	}
}
