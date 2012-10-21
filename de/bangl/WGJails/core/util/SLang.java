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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t7seven7t
 * @author BangL <henno.rickowski@googlemail.com>
 */
public class SLang {

    public static List<String> errorMessages = new ArrayList<String>();
    public static List<String> messages = new ArrayList<String>();

    static {
        errorMessages.add("mustbeplayer: You must be a player to do this");
        errorMessages.add("argcount: Incorrect argument count: ");
        errorMessages.add("permission: You do not have sufficient permissions to use this");
    }

    public static void load() {
        SPersist.load(null, SLang.class, "lang");
    }

    public String getErrorMessage(String s) {
        for (String ln : errorMessages) {
            if (ln.substring(0, ln.indexOf(": ")).equalsIgnoreCase(s)) {
                return ln.substring(ln.indexOf(": ") + 2);
            }
        }
        return null;
    }

    public String getMessage(String s) {
        for (String ln : messages) {
            if (ln.substring(0, ln.indexOf(": ")).equalsIgnoreCase(s)) {
                return ln.substring(ln.indexOf(": ") + 2);
            }
        }
        return null;	
    }
}
