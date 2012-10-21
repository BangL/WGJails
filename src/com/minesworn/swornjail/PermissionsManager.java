package com.minesworn.swornjail;

import com.minesworn.core.permissions.PermissionBase;

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
			this.node = (SwornJail.p.getName() + "." + node).toLowerCase();
		}
	}
	
}
