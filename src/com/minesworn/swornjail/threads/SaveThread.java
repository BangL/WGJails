package com.minesworn.swornjail.threads;

import org.bukkit.Bukkit;

import com.minesworn.core.threads.SThread;
import com.minesworn.swornjail.SwornJail;

public class SaveThread extends SThread {
	@Override
	public void run() {
		try {
			int i = 600;
			while (SwornJail.enabled) {
				Thread.sleep(1000);
				i--;
				if (i <= 0) {
					Bukkit.getScheduler().scheduleAsyncDelayedTask(SwornJail.p, new Runnable() {
						@Override
						public void run() {
							SwornJail.inmates.save();
						}
					});
					i = 600;
				}
			}
		} catch (InterruptedException e) {}
	}
}
