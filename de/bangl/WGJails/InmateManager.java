package de.bangl.WGJails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bangl.WGJails.core.util.Util;
import de.bangl.WGJails.events.JailEvent;
import de.bangl.WGJails.events.UnjailEvent;
import de.bangl.WGJails.objects.InmateEntry;
import de.bangl.WGJails.threads.InmateCounterThread;

public class InmateManager {

	public boolean isJailed(OfflinePlayer inmate) {
		return inJailList(inmate) && WGJailsPlugin.inmates.getInmate(inmate.getName()).isJailed();
	}
	
	public boolean inJailList(OfflinePlayer inmate) {
		return (WGJailsPlugin.inmates.getInmate(inmate.getName()) != null);
	}
	
	public boolean jail(OfflinePlayer inmate, long time, String reason, String jailer) {
		if (isJailed(inmate))
			return false;
		
		InmateEntry entry = new InmateEntry(time, reason, jailer);
		
		JailEvent jailevent = new JailEvent(inmate, entry);
		Bukkit.getPluginManager().callEvent(jailevent);
		
		if (jailevent.isCancelled())
			return false;
		
		WGJailsPlugin.inmates.addInmate(inmate.getName(), entry);
		
		if (inmate.isOnline()) {
			Player p = inmate.getPlayer();
			
			//Check if the player is mounting another entity and if so eject them.
			if (p.isInsideVehicle())
				p.getVehicle().eject(); 
			
			//Teleport the new inmate into jail and notify them.
			p.teleport(WGJailsPlugin.jail.getSpawn());
			msg(p, WGJailsPlugin.lang.getMessage("jail"), reason, jailer);
			
			//Begin thread to count down this inmates jail time.
			new InmateCounterThread(p);
		}
		
		return true;
	}
	
	public boolean unjail(OfflinePlayer inmate, String unjailer) {
		if (!isJailed(inmate))
			return false;
		
		UnjailEvent unjailevent = new UnjailEvent(inmate, unjailer);
		Bukkit.getPluginManager().callEvent(unjailevent);
		
		if (unjailevent.isCancelled())
			return false;
		
		if (inmate.isOnline()) {
			WGJailsPlugin.inmates.getInmate(inmate.getName()).unjail();
			WGJailsPlugin.inmates.removeInmate(inmate.getName());
			release(inmate.getPlayer());
			msg(inmate.getPlayer(), WGJailsPlugin.lang.getMessage("unjail"));
		} else
			WGJailsPlugin.inmates.getInmate(inmate.getName()).unjail();
		
		return true;
	}
	
	public boolean toggleMute(OfflinePlayer inmate) {
		InmateEntry entry = WGJailsPlugin.inmates.getInmate(inmate.getName());
		entry.setMuted(!entry.isMuted());
		return entry.isMuted();
	}
	
	public void changeReason(OfflinePlayer inmate, String newReason) {
		WGJailsPlugin.inmates.getInmate(inmate.getName()).setReason(newReason);
	}
	
	public void changeTime(OfflinePlayer inmate, long newTime) {
		WGJailsPlugin.inmates.getInmate(inmate.getName()).setTime(newTime);
	}
	
	public String getJailString(OfflinePlayer inmate, boolean personal) {
		InmateEntry entry = WGJailsPlugin.inmates.getInmate(inmate.getName());
		return Util.parseMsg(WGJailsPlugin.lang.getMessage((personal) ? "jailstatus" : "jailreason"), 
				(personal) ? "You" : inmate.getName(), 
				Util.formatTime(entry.getTime()),
				entry.getReason(),
				entry.getJailer());
	}
	
	public void release(Player p) {
		p.teleport(WGJailsPlugin.jail.getExit());
	}
	
	public void msg(Player p, String s, Object... args) {
		p.sendMessage(ChatColor.RED + Util.parseMsg(s, args));
	}
	
}
