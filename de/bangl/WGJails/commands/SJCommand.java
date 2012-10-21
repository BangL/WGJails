package de.bangl.WGJails.commands;

import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.core.commands.SCommand;
import de.bangl.WGJails.core.util.Util;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.exceptions.PlayerNotFoundException;

public abstract class SJCommand extends SCommand<WGJailsPlugin> {
	
	public OfflinePlayer getTarget(String name, boolean shouldBeJailed) throws PlayerNotFoundException, Exception {
		OfflinePlayer target = Util.matchOfflinePlayer(name);
		
		if (target == null) {
			errorMessage(WGJailsPlugin.lang.getErrorMessage("doesnotexist"), name);
			throw new PlayerNotFoundException();
		}
		
		if (WGJailsPlugin.inmatemanager.isJailed(target) != shouldBeJailed) {
			errorMessage(WGJailsPlugin.lang.getErrorMessage((shouldBeJailed) ? "notjailed" : "alreadyjailed"), target.getName());
			throw new Exception();
		}
		
		return target;
	}
	
}
