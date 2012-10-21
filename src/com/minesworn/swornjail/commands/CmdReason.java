package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

public class CmdReason extends SJCommand {

	public CmdReason() {
		this.name = "jailreason";
		this.description = "Modifies a player's jail reason.";
		this.permission = Permission.MODIFY_REASON.node;
		this.requiredArgs.add("player");
		this.requiredArgs.add("reason");
	}
	
	@Override
	public void perform() {
		try {
			OfflinePlayer target = getTarget(args[0], true);
			StringBuilder newReason = new StringBuilder();
			for (int i = 1; i < args.length; i++){
				newReason.append(args[i] + " ");
			}
			
			newReason.deleteCharAt(newReason.lastIndexOf(" "));
			
			SwornJail.inmatemanager.changeReason(target, newReason.toString());
			SwornJail.log(SwornJail.lang.getMessage("logjailreason"),
					target.getName(),
					newReason.toString(),
					sender.getName());
			confirmMessage(SwornJail.lang.getMessage("confirmjailreason"),
					target.getName());
		} catch (Exception e) {return;}
	}

}
