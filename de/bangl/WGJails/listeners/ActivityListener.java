package de.bangl.WGJails.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.bangl.WGJails.WGJailsPlugin;

public class ActivityListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer()))
			WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
	}
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
			@Override
			public void run() {
				if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer()))
					WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
			}
		});
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (e.isCancelled())
			return;
		if (WGJailsPlugin.inmatemanager.isJailed(e.getPlayer()))
			WGJailsPlugin.inmates.getInmate(e.getPlayer().getName()).setLastActivity(System.currentTimeMillis());
	}
	
}
