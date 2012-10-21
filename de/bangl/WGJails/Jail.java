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
package de.bangl.WGJails;

import de.bangl.WGJails.core.util.SPersist;
import de.bangl.WGJails.objects.SimpleVector;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Jail {
    private transient JailStage jailStage = JailStage.REGION_DEFINED;
    private SimpleVector spawn, exit;
    private int spawnyaw, exityaw;
    private transient World w = Bukkit.getWorlds().get(0);
    private String worldname = new String();

    public Jail() {
        load();
    }

    public JailStage getJailStage() {
        return jailStage;
    }

    public World getWorld() {
        return this.w;
    }

    public void setWorld(World w) {
        this.w = w;
    }

    public Location getSpawn() {
        return this.simpleVectorToLocation(this.spawn, spawnyaw);
    }

    public void setSpawn(Location l) {
        this.spawn = new SimpleVector(l); this.spawnyaw = (int) l.getYaw();
    }

    public Location getExit() {
        return this.simpleVectorToLocation(this.exit, exityaw);
    }

    public void setExit(Location l) {
        this.exit = new SimpleVector(l); this.exityaw = (int) l.getYaw();
    }

    public Location simpleVectorToLocation(SimpleVector v, int yaw) {
        return new Location(w, v.x, v.y, v.z, yaw, 0F);
    }

    public boolean isJailSetup() {
        
        //TODO: Implement jail setup check
        
        if (spawn == null
                || exit == null
                || w == null) {
            return false;
        }
        return true;
    }

    public boolean isInside(Location l) {

        //TODO: Implement jail setup check

        return false;
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
