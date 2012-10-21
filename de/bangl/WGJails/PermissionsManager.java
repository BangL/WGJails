package de.bangl.WGJails;

import de.bangl.WGJails.core.permissions.PermissionBase;

public class PermissionsManager extends PermissionBase {

	public enum Permission {
		JAIL("jail"),
		UNJAIL("unjail"),
		MUTE("mute"),
		RELOAD("reload"),
		SET("set"),
		MODIFY_TIME("modify.time"),
		MODIFY_REASON("modify.reason"),
		CHECK("check"),
		STATUS("status"),
		HELP("help"),
		BUILD("build"),
		USE_COMMANDS("canusecommands");
		
		public final String node;
		Permission(final String node) {
			this.node = (WGJailsPlugin.p.getName() + "." + node).toLowerCase();
		}
	}
	
}
