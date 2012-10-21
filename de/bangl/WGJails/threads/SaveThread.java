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
package de.bangl.WGJails.threads;

import de.bangl.WGJails.WGJailsPlugin;
import de.bangl.WGJails.core.threads.SThread;
import org.bukkit.Bukkit;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class SaveThread extends SThread {
    @Override
    public void run() {
        try {
            int i = 600;
            while (WGJailsPlugin.enabled) {
                Thread.sleep(1000);
                i--;
                if (i <= 0) {
                    Bukkit.getScheduler().scheduleAsyncDelayedTask(WGJailsPlugin.p, new Runnable() {
                        @Override
                        public void run() {
                            WGJailsPlugin.inmates.save();
                        }
                    });
                    i = 600;
                }
            }
        } catch (InterruptedException e) {}
    }
}
