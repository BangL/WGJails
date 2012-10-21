package com.minesworn.swornjail.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.minesworn.swornjail.SwornJail;

public class ActivityListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (SwornJail.inmatemanager.isJailed(e.getPlayer()))
			SwornJail.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
	}
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SwornJail.p, new Runnable() {
			@Override
			public void run() {
				if (SwornJail.inmatemanager.isJailed(e.getPlayer()))
					SwornJail.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
			}
		});
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (e.isCancelled())
			return;
		if (SwornJail.inmatemanager.isJailed(e.getPlayer()))
			SwornJail.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
	}
	
}
