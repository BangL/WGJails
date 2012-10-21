package de.bangl.WGJails.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.Config;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.objects.InmateEntry;

public class InmateCounterThread extends SThread {
	final Player p;
	public InmateCounterThread(final Player p) {
		super();
		this.p = p;
	}
	
	@Override
	public void run() {		
		try{
			while (WGJailsPlugin.inmates.isLoading())
				Thread.sleep(1000);
			InmateEntry entry = WGJailsPlugin.inmates.getInmate(p.getName());
			long lastSpam = 0;
			
			while (WGJailsPlugin.enabled && entry.isJailed() && p.isOnline()) {
				Thread.sleep(1000);
				long now = System.currentTimeMillis();
				if (Config.inmatesTimeDoesNotCountDownWhileAFK && now - entry.getLastActivity() > 30000L && now - lastSpam > 20000L) {
					lastSpam = now;
					Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
						@Override
						public void run() {
							p.sendMessage(ChatColor.RED + WGJailsPlugin.lang.getMessage("afk"));
						}
					});
				} else {
					if (entry.getTime() < 0) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(WGJailsPlugin.p, new Runnable() {
							@Override
							public void run() {
								WGJailsPlugin.inmatemanager.unjail(p, null);
							}
						});
						WGJailsPlugin.log(WGJailsPlugin.lang.getMessage("logfinishsentence"), p.getName());
					} else
						entry.setTime(entry.getTime() - 1000);
				}
			}
		} catch (InterruptedException e) {}
	}

}
