package com.minesworn.swornjail.commands;

import org.bukkit.OfflinePlayer;

import com.minesworn.core.commands.SCommand;
import com.minesworn.core.util.Util;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.exceptions.PlayerNotFoundException;

public abstract class SJCommand extends SCommand<SwornJail> {
	
	public OfflinePlayer getTarget(String name, boolean shouldBeJailed) throws PlayerNotFoundException, Exception {
		OfflinePlayer target = Util.matchOfflinePlayer(name);
		
		if (target == null) {
			errorMessage(SwornJail.lang.getErrorMessage("doesnotexist"), name);
			throw new PlayerNotFoundException();
		}
		
		if (SwornJail.inmatemanager.isJailed(target) != shouldBeJailed) {
			errorMessage(SwornJail.lang.getErrorMessage((shouldBeJailed) ? "notjailed" : "alreadyjailed"), target.getName());
			throw new Exception();
		}
		
		return target;
	}
	
}
