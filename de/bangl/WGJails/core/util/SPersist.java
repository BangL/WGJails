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
package de.bangl.WGJails.core.util;

import de.bangl.WGJails.core.SPlugin;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class SPersist {

    public static <T> void save(T instance, Class<? extends T> clazz) {
        save(instance, clazz, clazz.getSimpleName().toLowerCase());
    }

    public static <T> void save(Class<? extends T> clazz) {
        save(null, clazz);
    }

    public static <T> void save(T instance, Class<? extends T> clazz, String fName) {
        save(instance, clazz, new File(SPlugin.p.getDataFolder(), fName));
    }

    public static <T> void save(T instance, Class<? extends T> clazz, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    f.setAccessible(true);
                    fc.set(f.getName(), f.get(instance));
                }
            }

            fc.save(file);
        } catch (Exception e) {
            SPlugin.log("Exception ocurred while attempting to save file: " + file.getName());
            e.printStackTrace();
        }
    }

    public static <T> void load(T instance, Class<? extends T> clazz) {
        load(instance, clazz, clazz.getSimpleName().toLowerCase());
    }

    public static <T> void load(Class<? extends T> clazz) {
        load(null, clazz);
    }

    public static <T> void load(T instance, Class<? extends T> clazz, String fName) {
        load(instance, clazz, new File(SPlugin.p.getDataFolder(), fName));
    }

    public static <T> void load(T instance, Class<? extends T> clazz, File file) {
        try {
            if (!file.exists()) {
                save(instance, clazz, file);
            }

            FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    if (fc.get(f.getName()) == null) {
                        addDefaults(f, fc, instance);
                    }
                    f.setAccessible(true);
                    f.set(instance, fc.get(f.getName()));
                }
            }

            fc.save(file);
        } catch (Exception e) {
            SPlugin.log("Exception ocurred while attempting to load file: " + file.getName());
            e.printStackTrace();
        }
    }

    public static Object readLine(File file, String f) {
        try {			
            FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
            return fc.get(f);
        } catch (Exception e) {
            SPlugin.log("Exception ocurred while attempting to read: " + file.getName());
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void loadDefaultsFromInstance(T instance, T defaults) {
        for (Field f : instance.getClass().getDeclaredFields()) {
            if (!Modifier.isTransient(f.getModifiers())) {
                f.setAccessible(true);
                try {
                    f.set(instance, f.get(defaults));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void addDefaults(Field f, FileConfiguration fc, T instance) {
        try {
            f.setAccessible(true);
            fc.set(f.getName(), f.get(instance));
        } catch (Exception e) {
            SPlugin.log("Exception ocurred while attempting to add defaults to file.");
            e.printStackTrace();
        }
    }
}
