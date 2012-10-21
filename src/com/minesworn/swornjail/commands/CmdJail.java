package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.core.util.Util;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

public class CmdJail extends SJCommand {

	public CmdJail() {
		this.name = "jail";
		this.description = "Jail players";
		this.permission = Permission.JAIL.node;
		this.requiredArgs.add("player");
		this.requiredArgs.add("time");
		this.requiredArgs.add("reason");
	}
	
	@Override
	public void perform() {
		if (!SwornJail.jail.isJailSetup()) {
			errorMessage(SwornJail.lang.getErrorMessage("jailnotsetup"));
			return;
		}
	
		OfflinePlayer target;
		long time;
		StringBuilder reason = new StringBuilder();
		try {
			target = getTarget(args[0], false);
			time = Util.parseTime(args[1]);
		} catch (Exception e) {
			if (e != null && e.getMessage() != null)
				if (e.getMessage().equals("badtime"))
				errorMessage(SwornJail.lang.getErrorMessage("timeformat"));
			return;
		}
		
		if (time < 1000) {
			errorMessage(SwornJail.lang.getErrorMessage("outoftimerange"));
			return;
		}
		
		for (int i = 2; i < args.length; i++) {
			reason.append(args[i] + " ");
		}
		
		if (reason.lastIndexOf(" ") != -1)		
			reason.deleteCharAt(reason.lastIndexOf(" "));
		
		if (!SwornJail.inmatemanager.jail(target, time, reason.toString(), sender.getName())) {
			errorMessage(SwornJail.lang.getErrorMessage("eventcancelled"));
			return;
		}
		
		SwornJail.log(SwornJail.lang.getMessage("logjail"), target.getName(), sender.getName(), reason.toString());
		confirmMessage(SwornJail.lang.getMessage("confirmjail"), target.getName());
	}

}
