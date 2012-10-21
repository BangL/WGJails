package de.bangl.WGJails;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import de.bangl.WGJails.core.SPlugin;
import de.bangl.WGJails.commands.SJCommandRoot;
import de.bangl.WGJails.lang.Lang;
import de.bangl.WGJails.listeners.ActivityListener;
import de.bangl.WGJails.listeners.BlockListener;
import de.bangl.WGJails.listeners.PlayerListener;
import de.bangl.WGJails.objects.SimpleVector;
import de.bangl.WGJails.threads.InmateCounterThread;
import de.bangl.WGJails.threads.JailRelocateThread;
import de.bangl.WGJails.threads.SaveThread;

public class WGJailsPlugin extends SPlugin {
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
				Bukkit.getPluginManager().registerEvents(new ActivityListener(), WGJailsPlugin.p);
				Bukkit.getPluginManager().registerEvents(new PlayerListener(), WGJailsPlugin.p);	
				if (Config.blockProtectionEnabled)
					Bukkit.getPluginManager().registerEvents(new BlockListener(), WGJailsPlugin.p);
			}
			
		});
	}
	
}
