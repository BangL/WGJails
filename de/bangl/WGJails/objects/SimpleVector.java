/*
 * Copyright (C) 2012 t7seven7t
 *                    BangL <henno.rickowski@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.bangl.WGJails.objects;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
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
