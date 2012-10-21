package com.minesworn.swornjail.commands;

import com.minesworn.core.commands.SCommandRoot;
import com.minesworn.swornjail.SwornJail;

public class SJCommandRoot extends SCommandRoot<SwornJail> {
	private CmdCheck CMD_CHECK = new CmdCheck();
	private CmdJail CMD_JAIL = new CmdJail();
	private CmdMute CMD_MUTE = new CmdMute();
	private CmdReason CMD_REASON = new CmdReason();
	private CmdSet CMD_SET = new CmdSet();
	private CmdStatus CMD_STATUS = new CmdStatus();
	private CmdTime CMD_TIME = new CmdTime();
	private CmdUnjail CMD_UNJAIL = new CmdUnjail();
	private CmdReload CMD_RELOAD = new CmdReload();
	
	public SJCommandRoot() {
		super();
		this.addCommand(CMD_CHECK);
		this.addCommand(CMD_JAIL);
		this.addCommand(CMD_MUTE);
		this.addCommand(CMD_REASON);
		this.addCommand(CMD_SET);
		this.addCommand(CMD_STATUS);
		this.addCommand(CMD_TIME);
		this.addCommand(CMD_UNJAIL);
		this.addCommand(CMD_RELOAD);
	}
}
