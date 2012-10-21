package de.bangl.WGJails;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import de.bangl.WGJails.core.util.SPersist;
import de.bangl.WGJails.objects.SimpleVector;

public class Jail {
	private transient int jailStage = 0;
	private SimpleVector max, min, spawn, exit;
	private int spawnyaw, exityaw;
	private transient World w = Bukkit.getWorlds().get(0);
	private String worldname = new String();
	
	public Jail() {
		load();
	}
	
	public int getJailStage() {return jailStage;}
	public void nextJailStage() {
		jailStage++;
		
		if (jailStage > 4)
			this.resetJailStage();
	}
	public void resetJailStage() {jailStage = 0;}
	
	public void setMax(Vector v1) {
		Vector v2 = this.min.toVector();
				
		this.min = new SimpleVector(Vector.getMinimum(v1, v2));
		this.max = new SimpleVector(Vector.getMaximum(v1, v2));
	}
	public void setMin(Vector min) {this.min = new SimpleVector(min); this.max = null;}
	public World getWorld() {return this.w;}
	public void setWorld(World w) {this.w = w;}
	public Location getSpawn() {return this.simpleVectorToLocation(this.spawn, spawnyaw);}
	public void setSpawn(Location l) {
		this.spawn = new SimpleVector(l); this.spawnyaw = (int) l.getYaw();
	}
	public Location getExit() {return this.simpleVectorToLocation(this.exit, exityaw);}
	public void setExit(Location l) {
		this.exit = new SimpleVector(l); this.exityaw = (int) l.getYaw();
	}
	
	public Location simpleVectorToLocation(SimpleVector v, int yaw) {
		return new Location(w, v.x, v.y, v.z, yaw, 0F);
	}
	
	public boolean isJailSetup() {
		if (max == null || min == null || spawn == null || exit == null
				|| w == null || max.equals(min))
			return false;
		return true;
	}
	
	public boolean isInside(Location l) {
		SimpleVector v = new SimpleVector(l);
		if (min.x > v.x || min.y > v.y || min.z > v.z
				|| v.x > max.x || v.y > max.y || v.z > max.z)
			return false;
		return true;
	}
	
	public void load() {
		File f = new File(WGJailsPlugin.p.getDataFolder(), "jail.txt");
		
		/**
		 * For backwards compatibility check that the old plugin config exists and copy
		 * any settings from the old one over to the new Jail file.
		 */
		if (!f.exists()) {
			f = new File(WGJailsPlugin.p.getDataFolder(), "config.yml");
			if (f.exists()) {
				SPersist.load(this, Jail.class, "jail.txt");
				
				this.setMin(WGJailsPlugin.p.getConfig().getVector("jailregion.jailmin"));
				this.setMax(WGJailsPlugin.p.getConfig().getVector("jailregion.jailmax"));
				World w = Bukkit.getWorld(WGJailsPlugin.p.getConfig().getString("jailregion.jailworld"));
				this.setWorld((w != null) ? w : Bukkit.getWorlds().get(0));
				Vector spawn = WGJailsPlugin.p.getConfig().getVector("jailregion.jailspawn");
				this.setSpawn(new Location(w, spawn.getX(), spawn.getY(), spawn.getZ(),
						WGJailsPlugin.p.getConfig().getInt("jailregion.jailspawnyaw"), 0F));
				Vector exit = WGJailsPlugin.p.getConfig().getVector("jailregion.jailexit");
				this.setExit(new Location(w, exit.getX(), exit.getY(), exit.getZ(),
						WGJailsPlugin.p.getConfig().getInt("jailregion.jailexityaw"), 0F));

				save();
				//Finally delete the old config file as no more classes need to use it.
				f.delete();
				return;
			}
		}
		
		SPersist.load(this, Jail.class, "jail.txt");
		w = Bukkit.getWorld(worldname);
	}
	
	public void save() {
		this.setWorld((w != null) ? w : Bukkit.getWorlds().get(0));
		worldname = w.getName();
		SPersist.save(this, Jail.class, "jail.txt");
	}
	
}
