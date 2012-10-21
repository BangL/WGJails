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

import de.bangl.WGJails.core.threads.SThread;
import de.bangl.WGJails.core.util.SPersist;
import de.bangl.WGJails.exceptions.InmatesStillLoadingException;
import de.bangl.WGJails.objects.InmateEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class Inmates {
    private static File FOLDER = new File(WGJailsPlugin.p.getDataFolder(), "inmates");

    private boolean loading;
    private volatile transient Map<String, InmateEntry> inmates = new HashMap<String, InmateEntry>();

    public Inmates() {
        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
            File f = new File(WGJailsPlugin.p.getDataFolder(), "inmates.dat");
            if (f.exists()) {
                loadOldFile();
                f.delete();
                save();
                return;
            }
        }
        loading = true;
        loadAllInmates();
    }

    private void loadAllInmates() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(WGJailsPlugin.p, new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                Map<String, InmateEntry> loadingInmates = new HashMap<String, InmateEntry>();
                for (File file : FOLDER.listFiles()) {
                    String name = file.getName();
                    loadingInmates.put(name, loadInmate(name));
                }
                inmates = Collections.unmodifiableMap(loadingInmates);
                loading = false;
                WGJailsPlugin.log("Inmates loaded! [" + (System.currentTimeMillis() - start) + "ms]");
            }
        });
    }

    private InmateEntry loadInmate(final String name) {
        final InmateEntry i = new InmateEntry();
        SPersist.load(i, InmateEntry.class, "inmates/" + name);
        return i;
    }

    public InmateEntry getInmate(String name) {
        return inmates.get(name);
    }

    public void addInmate(String name, InmateEntry i) {
        new AddInmateThread(name, i);
    }

    public void removeInmate(String name) {
        new RemoveInmateThread(name);
    }

    public Map<String, InmateEntry> getInmates() throws InmatesStillLoadingException {
        if (loading) {
            throw new InmatesStillLoadingException();
        } else {
            return Collections.unmodifiableMap(inmates);
        }
    }

    public void save() {
        WGJailsPlugin.log("Saving inmates to disk...");
        try {
            long start = System.currentTimeMillis();
            for (Entry<String, InmateEntry> entry : getInmates().entrySet()) {
                    SPersist.save(entry.getValue(), InmateEntry.class, "inmates/" + entry.getKey());
            }
            WGJailsPlugin.log("Inmates saved! [" + (System.currentTimeMillis() - start) + "ms]");
        } catch (InmatesStillLoadingException e) {
            WGJailsPlugin.log("Cannot save inmates before they have even loaded!");
        }
    }

    public void loadOldFile() {
        File f = new File(WGJailsPlugin.p.getDataFolder(), "inmates.dat");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String s;
            Map<String, InmateEntry> loadingInmates = new HashMap<String, InmateEntry>();
            while((s = reader.readLine()) != null) {
                    String[] ss = s.split("\\|");
                    InmateEntry i = new InmateEntry(Math.round(Double.parseDouble(ss[1])) * 1000, ss[2], ss[3]);
                    loadingInmates.put(Bukkit.getOfflinePlayer(ss[0]).getName(), i);
            }
            inmates = Collections.unmodifiableMap(loadingInmates);
            reader.close();
        } catch (IOException e) {
            WGJailsPlugin.log("Failed to load inmates from old file: inmates.dat");
            e.printStackTrace();
        }
    }

    //Check if the inmates are still loading from disk before requesting the collection of them.
    public boolean isLoading() {
        return loading;
    }

    private class AddInmateThread extends SThread {
        final String name;
        final InmateEntry i;
        public AddInmateThread(String name, InmateEntry i) {
            super();
            this.name = name;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                while (loading) {
                    Thread.sleep(1000);
                }

                Map<String, InmateEntry> copy = new HashMap<String, InmateEntry>();
                copy.putAll(inmates);
                copy.put(name, i);

                inmates = Collections.unmodifiableMap(copy);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    private class RemoveInmateThread extends SThread {
        final String name;
        public RemoveInmateThread(String name) {
            super();
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (loading) {
                    Thread.sleep(1000);
                }

                Map<String, InmateEntry> copy = new HashMap<String, InmateEntry>();
                copy.putAll(inmates);
                copy.remove(name);

                inmates = Collections.unmodifiableMap(copy);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
