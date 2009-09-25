﻿/**
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.owfs.ownet.OWNet;

public class OwClient {

	/**
	 * An enumeration of the 1-wire devices supported by OwClient.
	 */
	public static enum DeviceType {
		DS18S20, DS2438, DS2409
	};

	private final static String[] familyCodes = { "10", "26", "81", "1F" };

	OWNet ownet = null;
	OwDeviceFactory odf = null;

	public OwClient(OWNet ownet) throws IllegalArgumentException {
		if (ownet == null) {
			throw new IllegalArgumentException(
					"Failed to contstruct OwClient with OWNet == null. Cannot contstruct OwClient object without OWNet object.");
		}
		this.ownet = ownet;
		OwDeviceFactory.init(this);
		this.odf = new OwDeviceFactory();
	}

	public OwDevice find(String id) throws UnsupportedDeviceException,
			DeviceNotFoundException {
		if (!OwDeviceFactory.canCreate(id)) {
			throw new IllegalArgumentException("Device type is not supported");
		}
		String[] str = { "/" };
		String path = getDevicePath(id, str, null);
		return odf.createDevice(path);
	}

	/**
	 * Returns a collection of all devices attached to this 1-wire network.
	 * 
	 * @return List<OwDevice> collection of attached devices.
	 */
	public List<OwDevice> list() {
		String[] in = { "/" };
		List<OwDevice> list = list("*", in, null, null);
		return list;
	}

	/**
	 * Returns a collection of all devices of a specific device family attached
	 * to this 1-wire network.
	 * 
	 * @param familyCode
	 *            representing the kind of devices to include
	 * @return A collection of attached devices.
	 * @throws UnsupportedDeviceException
	 */
	public List<OwDevice> list(String familyCode)
			throws UnsupportedDeviceException {
		String[] in = { "/" };
		if (!isSupported(familyCode)) {
			throw new UnsupportedDeviceException("The device type: "
					+ familyCode + " is not supported.");
		}

		List<OwDevice> list = list(familyCode, in, null, null);
		return list;
	}

	public List<OwDevice> list(String familyCode, String path) {
		String[] in = { path }; // TODO Check valid path
		List<OwDevice> list = list("*", in, null, null);
		return list;
	}

	/**
	 * @param familyCode
	 * @param in
	 * @param devices
	 * @param deviceIdCache
	 * @return
	 */
	protected List<OwDevice> list(String familyCode, String[] in,
			List<OwDevice> devices, List<String> deviceIdCache) {
		if (devices == null) {
			devices = new ArrayList<OwDevice>(5);
		}
		if (deviceIdCache == null) {
			deviceIdCache = new ArrayList<String>(5);
		}

		ArrayList<String> paths = new ArrayList<String>(5);

		for (String i : in) {
			String[] str = this.dir(i);
			if (str != null) {
				for (String s : str) {
					paths.add(s);
				}
			}
		}

		ArrayList<String> next = new ArrayList<String>(10);

		for (String p : paths) {
			String curId = p.substring(p.lastIndexOf("/") + 1);
			String curFamilyCode = curId.substring(0, 2);
			if (curId.startsWith("1F")) {
				if (!deviceIdCache.contains(curId)) {
					deviceIdCache.add(curId);
					next.add(p + "/main/");
				}
			}
			if (isSupportedSensor(curFamilyCode)) {
				if (familyCode.equalsIgnoreCase("*")
						|| curFamilyCode.equalsIgnoreCase(familyCode)) {
					OwDevice owd;
					try {
						owd = odf.createDevice(p);
						devices.add(owd);
					} catch (UnsupportedDeviceException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (!next.isEmpty()) {
			String str[] = new String[next.size()];
			next.toArray(str);
			return list(familyCode, str, devices, deviceIdCache);
		}

		return devices;
	}

	public synchronized String[] dir(String path) {
		String[] list = null;
		try {
			ownet.Connect();
			list = ownet.DirAll(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ownet.Disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	public synchronized String read(String path) {
		String msg = null;
		try {
			ownet.Connect();
			msg = ownet.Read(path); // TODO Verify correct path
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ownet.Disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}

	protected String getDevicePath(String id, String[] in,
			List<String> deviceIdCache) {

		if (deviceIdCache == null) {
			deviceIdCache = new ArrayList<String>(5);
		}
		ArrayList<String> paths = new ArrayList<String>(5);
		String path = null;
		for (String i : in) {
			String[] str = this.dir(i);
			if (str != null) {
				for (String s : str) {
					paths.add(s);
				}
			}
		}

		ArrayList<String> next = new ArrayList<String>(10);

		for (String p : paths) {
			String curId = p.substring(p.lastIndexOf("/") + 1);
			if (curId.equalsIgnoreCase(id)) {
				return p;
			}
			if (curId.startsWith("1F")) {
				if (!deviceIdCache.contains(curId)) {
					deviceIdCache.add(curId);
					next.add(p + "/main/");
				}
			}
		}
		if (!next.isEmpty()) {
			String str[] = new String[next.size()];
			next.toArray(str);
			path = getDevicePath(id, str, deviceIdCache);
			if (path != null) {
				return path;
			}
		}

		return path;
	}

	/**
	 * Checks if a <i>sensor</i> identified by a 1-wire ID or family code is
	 * supported by this implementation.
	 * 
	 * @param idOrFamilyCode
	 *            to check if it is supported
	 * @return <code>true</code> if the ID or family code is supported and
	 *         <code>false</code> if not.
	 */
	public static boolean isSupportedSensor(String idOrFamilyCode) {

		idOrFamilyCode = idOrFamilyCode.trim();
		if (idOrFamilyCode.length() > 2) {
			idOrFamilyCode = idOrFamilyCode.substring(0, 2);
		}
		if (idOrFamilyCode.equalsIgnoreCase("1F")) {
			return false;
		} else {
			return isSupported(idOrFamilyCode);
		}
	}

	/**
	 * Checks if <i>any device</i> identified by a 1-wire ID or family code is
	 * supported by this implementation.
	 * 
	 * @param idOrFamilyCode
	 *            to check if it is supported
	 * @return <code>true</code> if the ID or family code is supported and
	 *         <code>false</code> if not.
	 */
	public static boolean isSupported(String idOrFamilyCode) {
		boolean supported = false;

		idOrFamilyCode = idOrFamilyCode.trim();
		if (idOrFamilyCode.length() > 2) {
			idOrFamilyCode = idOrFamilyCode.substring(0, 2);
		}

		for (String code : familyCodes) {
			if (code.equalsIgnoreCase(idOrFamilyCode)) {
				supported = true;
			}
		}

		return supported;
	}

	public void print() {

		String[] str = { "/" };
		print(str, null);
	}

	protected synchronized void print(String[] in, List<String> deviceIdCache) {
		ArrayList<String> paths = new ArrayList<String>(5);

		for (String i : in) {
			String[] str = this.dir(i);
			if (str != null) {
				for (String s : str) {
					paths.add(s);
				}
			}
		}

		ArrayList<String> next = new ArrayList<String>(10);

		for (String p : paths) {
			String id = p.substring(p.lastIndexOf("/") + 1);
			if (id.startsWith("10.") || id.startsWith("26.")
					|| id.startsWith("81.")) {
				if (!deviceIdCache.contains(id)) {
					deviceIdCache.add(id);
				}
			}
			if (id.startsWith("1F")) {
				if (!deviceIdCache.contains(id)) {
					deviceIdCache.add(id);
					next.add(p + "/main/");
				}

			}
		}
		if (!next.isEmpty()) {
			String str[] = new String[next.size()];
			next.toArray(str);
			print(str, deviceIdCache);
		}
	}
}
