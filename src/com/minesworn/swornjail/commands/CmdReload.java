package com.minesworn.swornjail.commands;

import com.minesworn.swornjail.PermissionsManager.Permission;
import com.minesworn.swornjail.SwornJail;

public class CmdReload extends SJCommand {

	public CmdReload() {
		this.name = "jailreload";
		this.description = "reload the plugin";
		this.permission = Permission.RELOAD.node;
	}
	
	@Override
	public void perform() {
		SwornJail.p.reload();
		confirmMessage(SwornJail.lang.getMessage("confirmreload"));
		SwornJail.log(SwornJail.lang.getMessage("logreload"), sender.getName());
	}

}
