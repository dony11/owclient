/**
 * Module: owclient/java
 * 
 * Copyright (C) 2009 Martin Strandbygaard
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.strandbygaard.onewire.owclient;

public class OwDeviceFactory {

	private static OwClient owc = null;
	private static boolean isInitialized = false;

	public OwDeviceFactory() {
	}

	public static void init(OwClient oc) {
		if (!isInitialized) {
			if (owc == null && oc != null) {
				owc = oc;
				isInitialized = true;
			}
		}
	}

	public OwDevice createDevice(String path) {
		OwDevice dev = null;
		dev = construct(path);
		return dev;
	}

	private OwDevice construct(String path) {
		OwDevice dev = null;
		String id = path.substring(path.lastIndexOf("/") + 1);

		if (!canCreate(id)) {
			return dev;
		}

		if (id.startsWith("26.")) {
			dev = new DS2438(path, owc);
		}

		if (id.startsWith("10.")) {
			dev = new DS18S20(path, owc);
		}

		return dev;
	}

	public static boolean canCreate(String id) {
		boolean b = false;
		if (id.startsWith("10.") || id.startsWith("26.")
				|| id.startsWith("81.") || id.startsWith("1F.")) {
			b = true;
		}
		return b;
	}
}
