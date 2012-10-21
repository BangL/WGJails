package de.bangl.WGJails.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.PermissionsManager.Permission;

public class CmdSet extends SJCommand {

	public CmdSet() {
		this.name = "jailset";
		this.description = "Set the region of jail";
		this.permission = Permission.SET.node;
		this.optionalArgs.add("cancel");
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (args.length == 1) {
			WGJailsPlugin.jail.resetJailStage();
			confirmMessage(WGJailsPlugin.lang.getMessage("setcancelled"));
			return;
		}
		
		Location l = player.getLocation();
		
		switch (WGJailsPlugin.jail.getJailStage()) {
		case 0: 
			confirmMessage(WGJailsPlugin.lang.getMessage("jailset1"));
			break;
		case 1: 
			WGJailsPlugin.jail.setWorld(l.getWorld());
			WGJailsPlugin.jail.setMin(l.toVector().toBlockVector());
			msg(ChatColor.RED + "Corner 1 set!");
			confirmMessage(WGJailsPlugin.lang.getMessage("jailset2"));
			break;
		case 2: 
			WGJailsPlugin.jail.setMax(l.toVector().toBlockVector());
			msg(ChatColor.RED + "Corner 2 set!");
			confirmMessage(WGJailsPlugin.lang.getMessage("jailset3"));
			break;
		case 3: 
			WGJailsPlugin.jail.setSpawn(l);
			msg(ChatColor.RED + "Jail spawn set!");
			confirmMessage(WGJailsPlugin.lang.getMessage("jailset4"));
			break;
		case 4: 
			WGJailsPlugin.jail.setExit(l);
			msg(ChatColor.RED + "Jail exit set!");
			confirmMessage(WGJailsPlugin.lang.getMessage("jailset5"));
			break;
		default: 
			break;
		}
		WGJailsPlugin.jail.nextJailStage();
	}
	
}
