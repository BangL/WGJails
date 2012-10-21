package de.bangl.WGJails.commands;

import de.bangl.WGJails.PermissionsManager.Permission;
import de.bangl.WGJails.WGJailsPlugin;

public class CmdReload extends SJCommand {

	public CmdReload() {
		this.name = "jailreload";
		this.description = "reload the plugin";
		this.permission = Permission.RELOAD.node;
	}
	
	@Override
	public void perform() {
		WGJailsPlugin.p.reload();
		confirmMessage(WGJailsPlugin.lang.getMessage("confirmreload"));
		WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logreload"), sender.getName());
	}

}
