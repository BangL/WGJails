package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

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
		
		if (!SwornJail.inmatemanager.unjail(target, sender.getName())) {
			errorMessage(SwornJail.lang.getErrorMessage("eventcancelled"));
			return;
		}
		
		SwornJail.log(SwornJail.lang.getMessage("logunjail"), target.getName(), sender.getName());
		confirmMessage(SwornJail.lang.getMessage("confirmunjail"), target.getName());
	}

}
