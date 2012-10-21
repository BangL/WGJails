package de.bangl.WGJails.commands;

import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.core.util.Util;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

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
				errorMessage(WGJailsPlugin.lang.getErrorMessage("outoftimerange"));
				return;
			}
						
			WGJailsPlugin.inmatemanager.changeTime(target, time);
			WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logjailtime"),
					target.getName(),
					Util.formatTime(time),
					sender.getName());
			confirmMessage(WGJailsPlugin.lang.getMessage("confirmjailtime"),
					target.getName());
		} catch (Exception e) {return;}
	}

}
