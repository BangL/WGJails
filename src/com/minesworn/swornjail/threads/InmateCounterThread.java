package com.minesworn.swornjail.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.minesworn.core.threads.SThread;
import com.minesworn.swornjail.Config;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.objects.InmateEntry;

public class InmateCounterThread extends SThread {
	final Player p;
	public InmateCounterThread(final Player p) {
		super();
		this.p = p;
	}
	
	@Override
	public void run() {		
		try{
			while (SwornJail.inmates.isLoading())
				Thread.sleep(1000);
			InmateEntry entry = SwornJail.inmates.getInmate(p.getName());
			long lastSpam = 0;
			
			while (SwornJail.enabled && entry.isJailed() && p.isOnline()) {
				Thread.sleep(1000);
				long now = System.currentTimeMillis();
				if (Config.inmatesTimeDoesNotCountDownWhileAFK && now - entry.getLastActivity() > 30000L && now - lastSpam > 20000L) {
					lastSpam = now;
					Bukkit.getScheduler().scheduleSyncDelayedTask(SwornJail.p, new Runnable() {
						@Override
						public void run() {
							p.sendMessage(ChatColor.RED + SwornJail.lang.getMessage("afk"));
						}
					});
				} else {
					if (entry.getTime() < 0) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(SwornJail.p, new Runnable() {
							@Override
							public void run() {
								SwornJail.inmatemanager.unjail(p, null);
							}
						});
						SwornJail.log(SwornJail.lang.getMessage("logfinishsentence"), p.getName());
					} else
						entry.setTime(entry.getTime() - 1000);
				}
			}
		} catch (InterruptedException e) {}
	}

}
