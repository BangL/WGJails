package de.bangl.WGJails.commands;

import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

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
			
			WGJailsPlugin.inmatemanager.changeReason(target, newReason.toString());
			WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logjailreason"),
					target.getName(),
					newReason.toString(),
					sender.getName());
			confirmMessage(WGJailsPlugin.lang.getMessage("confirmjailreason"),
					target.getName());
		} catch (Exception e) {return;}
	}

}
