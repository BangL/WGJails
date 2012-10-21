package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.core.util.Util;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

public class CmdTime extends SJCommand {

	public CmdTime() {
		this.name = "jailtime";
		this.description = "Change jail time for a player.";
		this.permission = Permission.MODIFY_TIME.node;
		this.requiredArgs.add("player");
		this.requiredArgs.add("time");
	}
	
	@Override
	public void perform() {
		try {
			OfflinePlayer target = getTarget(args[0], true);
			long time = Util.parseTime(args[1]);
			
			if (time < 1000) {
				errorMessage(SwornJail.lang.getErrorMessage("outoftimerange"));
				return;
			}
						
			SwornJail.inmatemanager.changeTime(target, time);
			SwornJail.log(SwornJail.lang.getMessage("logjailtime"),
					target.getName(),
					Util.formatTime(time),
					sender.getName());
			confirmMessage(SwornJail.lang.getMessage("confirmjailtime"),
					target.getName());
		} catch (Exception e) {return;}
	}

}
