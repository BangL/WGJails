package de.bangl.WGJails.commands;

import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

public class CmdUnjail extends SJCommand {

	public CmdUnjail() {
		this.name = "unjail";
		this.description = "Unjail players";
		this.permission = Permission.UNJAIL.node;
		this.requiredArgs.add("player");
	}
	
	@Override
	public void perform() {
		OfflinePlayer target;
		try {
			target = getTarget(args[0], true);
		} catch (Exception e) {return;}
		
		if (!WGJailsPlugin.inmatemanager.unjail(target, sender.getName())) {
			errorMessage(WGJailsPlugin.lang.getErrorMessage("eventcancelled"));
			return;
		}
		
		WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logunjail"), target.getName(), sender.getName());
		confirmMessage(WGJailsPlugin.lang.getMessage("confirmunjail"), target.getName());
	}

}
