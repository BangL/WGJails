package de.bangl.WGJails.threads;

import org.bukkit.Bukkit;

import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.WGJailsPlugin;

public class SaveThread extends SThread {
	@Override
	public void run() {
		try {
			int i = 600;
			while (WGJailsPlugin.enabled) {
				Thread.sleep(1000);
				i--;
				if (i <= 0) {
					Bukkit.getScheduler().scheduleAsyncDelayedTask(WGJailsPlugin.p, new Runnable() {
						@Override
						public void run() {
							WGJailsPlugin.inmates.save();
						}
					});
					i = 600;
				}
			}
		} catch (InterruptedException e) {}
	}
}
