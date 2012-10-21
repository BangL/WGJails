package de.bangl.WGJails.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

@SerializableAs("SimpleVector")
public class SimpleVector implements ConfigurationSerializable {

	public int x, y, z;
	
	public SimpleVector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public SimpleVector(String s) {
		String[] ss = s.split(",");
		
		this.x = Integer.parseInt(ss[0]);
		this.y = Integer.parseInt(ss[1]);
		this.z = Integer.parseInt(ss[2]);
	}
	
	public SimpleVector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public SimpleVector(Vector v) {
		this(v.getBlockX(), v.getBlockY(), v.getBlockZ());
	}
	
	public SimpleVector(Location l) {
		this(l.toVector());
	}
	
	public String toString() {
		return (x + "," + y + "," + z);
	}
	
	public Vector toVector() {
		return new Vector(x, y, z);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		result.put("c", toString());
		
		return result;
	}
	
	public static SimpleVector deserialize(Map<String, Object> args) {
		return new SimpleVector((String) args.get("c"));
	}
	
}