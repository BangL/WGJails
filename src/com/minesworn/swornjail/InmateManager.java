package com.minesworn.swornjail;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.minesworn.core.util.Util;
import com.minesworn.swornjail.events.JailEvent;
import com.minesworn.swornjail.events.UnjailEvent;
import com.minesworn.swornjail.objects.InmateEntry;
import com.minesworn.swornjail.threads.InmateCounterThread;

public class InmateManager {

	public boolean isJailed(OfflinePlayer inmate) {
		return inJailList(inmate) && SwornJail.inmates.getInmate(inmate.getName()).isJailed();
	}
	
	public boolean inJailList(OfflinePlayer inmate) {
		return (SwornJail.inmates.getInmate(inmate.getName()) != null);
	}
	
	public boolean jail(OfflinePlayer inmate, long time, String reason, String jailer) {
		if (isJailed(inmate))
			return false;
		
		InmateEntry entry = new InmateEntry(time, reason, jailer);
		
		JailEvent jailevent = new JailEvent(inmate, entry);
		Bukkit.getPluginManager().callEvent(jailevent);
		
		if (jailevent.isCancelled())
			return false;
		
		SwornJail.inmates.addInmate(inmate.getName(), entry);
		
		if (inmate.isOnline()) {
			Player p = inmate.getPlayer();
			
			//Check if the player is mounting another entity and if so eject them.
			if (p.isInsideVehicle())
				p.getVehicle().eject(); 
			
			//Teleport the new inmate into jail and notify them.
			p.teleport(SwornJail.jail.getSpawn());
			msg(p, SwornJail.lang.getMessage("jail"), reason, jailer);
			
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
			SwornJail.inmates.getInmate(inmate.getName()).unjail();
			SwornJail.inmates.removeInmate(inmate.getName());
			release(inmate.getPlayer());
			msg(inmate.getPlayer(), SwornJail.lang.getMessage("unjail"));
		} else
			SwornJail.inmates.getInmate(inmate.getName()).unjail();
		
		return true;
	}
	
	public boolean toggleMute(OfflinePlayer inmate) {
		InmateEntry entry = SwornJail.inmates.getInmate(inmate.getName());
		entry.setMuted(!entry.isMuted());
		return entry.isMuted();
	}
	
	public void changeReason(OfflinePlayer inmate, String newReason) {
		SwornJail.inmates.getInmate(inmate.getName()).setReason(newReason);
	}
	
	public void changeTime(OfflinePlayer inmate, long newTime) {
		SwornJail.inmates.getInmate(inmate.getName()).setTime(newTime);
	}
	
	public String getJailString(OfflinePlayer inmate, boolean personal) {
		InmateEntry entry = SwornJail.inmates.getInmate(inmate.getName());
		return Util.parseMsg(SwornJail.lang.getMessage((personal) ? "jailstatus" : "jailreason"), 
				(personal) ? "You" : inmate.getName(), 
				Util.formatTime(entry.getTime()),
				entry.getReason(),
				entry.getJailer());
	}
	
	public void release(Player p) {
		p.teleport(SwornJail.jail.getExit());
	}
	
	public void msg(Player p, String s, Object... args) {
		p.sendMessage(ChatColor.RED + Util.parseMsg(s, args));
	}
	
}
