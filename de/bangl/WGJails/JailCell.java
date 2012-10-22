/*
 * Copyright (C) 2012 BangL <henno.rickowski@googlemail.com>
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

import de.bangl.WGJails.objects.SimpleVector;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class JailCell {
    private String id;
    private SimpleVector spawn;
    private int spawnyaw;
    private OfflinePlayer inmate;
    private Boolean free;

    public JailCell(SimpleVector spawn, int spawnyaw) {
        init(UUID.randomUUID().toString(), spawn, spawnyaw, null, true);
    }

    public JailCell(String id, SimpleVector spawn, int spawnyaw, OfflinePlayer inmate) {
        init(id, spawn, spawnyaw, inmate, false);
    }

    private void init(String id, SimpleVector spawn, int spawnyaw, OfflinePlayer inmate, boolean free) {
        this.id = id;
        this.spawn = spawn;
        this.spawnyaw = spawnyaw;
        this.inmate = inmate;
        this.free = free;
    }

    public String getId() {
        return id;
    }

    public SimpleVector getSpawn() {
        return spawn;
    }

    public int getSpawnYaw() {
        return spawnyaw;
    }

    public void setInmate(OfflinePlayer inmate) {
        this.inmate = inmate;
        this.free = false;
    }

    public OfflinePlayer getInmate() {
        return inmate;
    }

    public Boolean isFree() {
        return free;
    }

}
