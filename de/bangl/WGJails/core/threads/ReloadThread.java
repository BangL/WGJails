package de.bangl.WGJails.core.threads;

import de.bangl.WGJails.core.SPlugin;

public class ReloadThread extends SThread {
	@Override
	public void run() {
		try {
			SPlugin.p.onDisable();
			Thread.sleep(2000);
			SPlugin.p.onEnable();
			SPlugin.p.afterReload();
		} catch (InterruptedException e) {}
	}

}
