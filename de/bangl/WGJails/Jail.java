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

import com.sk89q.worldguard.protection.managers.RegionManager;
import de.bangl.WGJails.core.util.SPersist;
import de.bangl.WGJails.objects.SimpleVector;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Jail {
    private transient JailStage jailStage = JailStage.REGION_DEFINED;
    private SimpleVector exit;
    private int exityaw;
    private transient World world = Bukkit.getWorlds().get(0);
    private String worldname = new String();
    private String region;
    private transient ArrayList<JailCell> cells;

    public Jail() {
        load();
    }

    public JailStage getJailStage() {
        return jailStage;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location getFreeCell() {
        ArrayList<JailCell> freeCells = this.getFreeCells();
        if (!freeCells.isEmpty()) {
            Integer rnd = (int) Math.random() * (freeCells.size() - 1);
            return this.simpleVectorToLocation(freeCells.get(rnd).getSpawn(), freeCells.get(rnd).getSpawnYaw());
        } else {
            return null;
        }
    }

    public void addCell(Location l) {
        JailCell newCell = new JailCell(new SimpleVector(l), (int) l.getYaw());
        this.cells.add(null);
    }

    public Location getExit() {
        //TODO: implement saving of old player pos on jail event.
        return this.simpleVectorToLocation(this.exit, exityaw);
    }

    public void setExit(Location l) {
        this.exit = new SimpleVector(l); this.exityaw = (int) l.getYaw();
    }

    public Location simpleVectorToLocation(SimpleVector v, int yaw) {
        return new Location(this.getWorld(), v.x, v.y, v.z, yaw, 0F);
    }

    public boolean isJailSetup() {
        
        //TODO: Implement jail setup check
        
        if (region == null
                || cells == null) {
            return false;
        }
        return true;
    }

    public boolean isInside(Location l) {
        World lworld = l.getWorld();
        RegionManager rm = WGJailsPlugin.pluginWorldGuard.getRegionManager(this.getWorld());
        if (this.getWorld().equals(lworld)
                && rm.hasRegion(region)
                && rm.getApplicableRegions(l).getFlag(WGJailsPlugin.FLAG_JAIL) != null) {
            return true;
        }
        return false;
    }

    public void load() {
        File f = new File(WGJailsPlugin.p.getDataFolder(), getJailFileName());
        SPersist.load(this, Jail.class, getJailFileName());
    }

    public void save() {
        SPersist.save(this, Jail.class, getJailFileName());
    }
    
    public String getregion() {
        return this.region;
    }

    public void setregion(String region) {
        this.region = region;
    }

    private ArrayList<JailCell> getFreeCells() {
        ArrayList<JailCell> result = new ArrayList<JailCell>();
        for (JailCell cell : this.cells) {
            if (cell.isFree()) {
                result.add(cell);
            }
        }
        return result;
    }

    public void clearCells() {
        this.cells.clear();
    }

    public String getJailFileName() {
        return region + ".jail";
    }
}
