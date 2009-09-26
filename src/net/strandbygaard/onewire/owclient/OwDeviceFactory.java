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

	public OwSensor createDevice(String path) throws UnsupportedDeviceException {
		OwSensor dev = null;
		dev = construct(path);
		return dev;
	}

	private OwSensor construct(String path) throws UnsupportedDeviceException {
		if (path == null) {
			return null;
		}

		OwSensor dev = null;
		String id = path.substring(path.lastIndexOf("/") + 1);
		id = id.substring(0, 2);
		id = id.toUpperCase();

		if (!canCreate(id)) {
			throw new UnsupportedDeviceException(
					"Unable to create device with family code: "
							+ id.substring(0, 2));
		}

		if (id.equalsIgnoreCase("10")) {
			dev = new DS18S20(path, owc);
		}

		if (id.equalsIgnoreCase("26")) {
			dev = new DS2438(path, owc);
		}

		// if (id.equalsIgnoreCase("81")) {
		// dev = new OwSensorImpl(path, owc) {
		// @Override
		// public double read(Reading r) {
		// return 0;
		// }
		//
		// public String getUnit(Reading r) {
		// // TODO Auto-generated method stub
		// return null;
		// }
		// };
		// }

		// if (id.equalsIgnoreCase("1F")) {
		// dev = new DS2409(path, owc);
		// }

		return dev;
	}

	/**
	 * Checks an 1-wire device specified by its unique ID can be created by this
	 * factory implementation.
	 * 
	 * It compares the passed value to the elements in
	 * {@link net.strandbygaard.onewire.owclient.OwDevice.supportedFamilyCodes}
	 * 
	 * @param id
	 *            - the ID of the 1-wire device to check if an implementation is
	 *            available.
	 * @return <code>true</code> if the the device is supported, and
	 *         <code>false</code> if not.
	 */
	public static boolean canCreate(String id) {
		boolean b = false;
		for (String fam : OwDevice.supportedFamilyCodes) {
			if (id.startsWith(fam)) {
				b = true;
			}
		}
		return b;
	}
}
