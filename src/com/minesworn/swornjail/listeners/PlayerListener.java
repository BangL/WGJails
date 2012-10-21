package com.minesworn.swornjail.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import com.minesworn.swornjail.Config;
import com.minesworn.swornjail.PermissionsManager.Permission;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.threads.InmateCounterThread;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (SwornJail.inmatemanager.inJailList(e.getPlayer())) {
			if (!SwornJail.inmates.getInmate(e.getPlayer().getName()).isJailed())
				SwornJail.inmatemanager.release(e.getPlayer());
			else 
				new InmateCounterThread(e.getPlayer());
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		if (SwornJail.inmatemanager.isJailed(e.getPlayer()) && Config.inmatesCannotPickupItems)
			e.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent e) {		
		if (SwornJail.inmatemanager.isJailed(e.getPlayer()) && Config.inmatesCannotDropItems) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + SwornJail.lang.getMessage("cannotdropitems"));
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (SwornJail.inmatemanager.isJailed(p)) {
			if (SwornJail.inmates.getInmate(p.getName()).isMuted()) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + SwornJail.lang.getMessage("muted"));
			}
		}		
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {	
		Player p = e.getPlayer();
		if (SwornJail.inmatemanager.isJailed(p)) {
			if (!p.hasPermission(Permission.USE_COMMANDS.node)) {
				if (!e.getMessage().equalsIgnoreCase("/jailstatus")) {
					for (String s : Config.inmatesWhitelistedCommands) {
						if (e.getMessage().matches("/" + s.toLowerCase() + ".*"))
							return;
					}
					
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + SwornJail.lang.getMessage("cannotUseCommands"));
				}
			}
		}
		
	}
	
}
