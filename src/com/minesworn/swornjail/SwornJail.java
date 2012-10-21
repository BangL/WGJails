package com.minesworn.swornjail;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import com.minesworn.core.SPlugin;
import com.minesworn.swornjail.commands.SJCommandRoot;
import com.minesworn.swornjail.lang.Lang;
import com.minesworn.swornjail.listeners.ActivityListener;
import com.minesworn.swornjail.listeners.BlockListener;
import com.minesworn.swornjail.listeners.PlayerListener;
import com.minesworn.swornjail.objects.SimpleVector;
import com.minesworn.swornjail.threads.InmateCounterThread;
import com.minesworn.swornjail.threads.JailRelocateThread;
import com.minesworn.swornjail.threads.SaveThread;

public class SwornJail extends SPlugin {
	public static Jail jail;
	public static Inmates inmates;
	public static InmateManager inmatemanager;
	
	@Override
	public void onEnable() {
		preEnable();
		ConfigurationSerialization.registerClass(SimpleVector.class);
		lang = new Lang();
		Lang.load();
		Config.load();
		log("Config loaded!");
		jail = new Jail();
		log("Jail loaded!");
		inmates = new Inmates();	
		inmatemanager = new InmateManager();
		commandRoot = new SJCommandRoot();
		
		registerEvents();
		
		new SaveThread();
		new JailRelocateThread();
	}
	
	@Override
	public void onDisable() {
		preDisable();
		jail.save();
		inmates.save();
	}
	
	@Override
	public void afterReload() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (inmatemanager.isJailed(p)) {
				new InmateCounterThread(p);
			}
		}
	}
	
	public void registerEvents() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				Bukkit.getPluginManager().registerEvents(new ActivityListener(), SwornJail.p);
				Bukkit.getPluginManager().registerEvents(new PlayerListener(), SwornJail.p);	
				if (Config.blockProtectionEnabled)
					Bukkit.getPluginManager().registerEvents(new BlockListener(), SwornJail.p);
			}
			
		});
	}
	
}
