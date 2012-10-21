package com.minesworn.swornjail.threads;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.minesworn.core.threads.SThread;
import com.minesworn.swornjail.SwornJail;
import com.minesworn.swornjail.exceptions.InmatesStillLoadingException;
import com.minesworn.swornjail.objects.InmateEntry;

public class JailRelocateThread extends SThread {
	
	@Override
	public void run() {
		try {
			while (SwornJail.inmates.isLoading())
				Thread.sleep(1000);
			while (SwornJail.enabled) {
				Thread.sleep(1000);
				if (SwornJail.jail.isJailSetup()) {
					for (Entry<String, InmateEntry> entry : SwornJail.inmates.getInmates().entrySet()) {
						if (!entry.getValue().isJailed())
							continue;
						OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
						if (p.isOnline()) {
							if (!SwornJail.jail.isInside(p.getPlayer().getLocation())) {
								p.getPlayer().teleport(SwornJail.jail.getSpawn());
								SwornJail.inmatemanager.msg(p.getPlayer(), SwornJail.lang.getMessage("escape"));
							}
						}
					}
				}
			}
		} catch (InterruptedException | InmatesStillLoadingException e) {}
	}

}
