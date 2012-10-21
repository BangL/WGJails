package com.minesworn.swornjail.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.PermissionsManager.Permission;

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
			SwornJail.jail.resetJailStage();
			confirmMessage(SwornJail.lang.getMessage("setcancelled"));
			return;
		}
		
		Location l = player.getLocation();
		
		switch (SwornJail.jail.getJailStage()) {
		case 0: 
			confirmMessage(SwornJail.lang.getMessage("jailset1"));
			break;
		case 1: 
			SwornJail.jail.setWorld(l.getWorld());
			SwornJail.jail.setMin(l.toVector().toBlockVector());
			msg(ChatColor.RED + "Corner 1 set!");
			confirmMessage(SwornJail.lang.getMessage("jailset2"));
			break;
		case 2: 
			SwornJail.jail.setMax(l.toVector().toBlockVector());
			msg(ChatColor.RED + "Corner 2 set!");
			confirmMessage(SwornJail.lang.getMessage("jailset3"));
			break;
		case 3: 
			SwornJail.jail.setSpawn(l);
			msg(ChatColor.RED + "Jail spawn set!");
			confirmMessage(SwornJail.lang.getMessage("jailset4"));
			break;
		case 4: 
			SwornJail.jail.setExit(l);
			msg(ChatColor.RED + "Jail exit set!");
			confirmMessage(SwornJail.lang.getMessage("jailset5"));
			break;
		default: 
			break;
		}
		SwornJail.jail.nextJailStage();
	}
	
}
