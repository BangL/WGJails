package de.bangl.WGJails.threads;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.exceptions.InmatesStillLoadingException;
import de.bangl.WGJails.objects.InmateEntry;

public class JailRelocateThread extends SThread {
	
	@Override
	public void run() {
		try {
			while (WGJailsPlugin.inmates.isLoading())
				Thread.sleep(1000);
			while (WGJailsPlugin.enabled) {
				Thread.sleep(1000);
				if (WGJailsPlugin.jail.isJailSetup()) {
					for (Entry<String, InmateEntry> entry : WGJailsPlugin.inmates.getInmates().entrySet()) {
						if (!entry.getValue().isJailed())
							continue;
						OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
						if (p.isOnline()) {
							if (!WGJailsPlugin.jail.isInside(p.getPlayer().getLocation())) {
								p.getPlayer().teleport(WGJailsPlugin.jail.getSpawn());
								WGJailsPlugin.inmatemanager.msg(p.getPlayer(), WGJailsPlugin.lang.getMessage("escape"));
							}
						}
					}
				}
			}
		} catch (InterruptedException | InmatesStillLoadingException e) {}
	}

}
