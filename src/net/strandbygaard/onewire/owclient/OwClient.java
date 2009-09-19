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

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.owfs.ownet.OWNet;

public class OwClient {

	/**
	 * An enumeration of the 1-wire devices supported by OwClient.
	 */
	public static enum DeviceType {
		DS18S20, DS2438, DS2409
	};

	OWNet ownet = null;
	private OwDeviceFactory odf = null;
	private ArrayList<String> deviceIdCache = null;

	public OwClient(OWNet ownet) throws IllegalArgumentException {
		if (ownet == null) {
			throw new IllegalArgumentException(
					"Failed to contstruct OwClient with OWNet == null. Cannot contstruct OwClient object without OWNet object.");
		}
		this.ownet = ownet;
		OwDeviceFactory.init(this);
		this.odf = new OwDeviceFactory();
		reset();
	}

	public OwDevice find(String id) throws IllegalArgumentException {
		if (!OwDeviceFactory.canCreate(id)) {
			throw new IllegalArgumentException("Device type is not supported");
		}
		reset();
		String[] str = { "/" };
		String path = getDevicePath(id, str);
		return odf.createDevice(path);
	}

	/**
	 * Returns a collection of all devices attached to this 1-wire network.
	 * 
	 * @return List<OwDevice> collection of attached devices.
	 */
	public List<OwDevice> list() {
		// TODO Return a collection of all devices
		return null;
	}

	/**
	 * Returns a collection of all devices of a specific device family attached
	 * to this 1-wire network.
	 * 
	 * @param familyCode
	 *            representing the kind of devices to include
	 * @return A collection of attached devices.
	 */
	public List<OwDevice> list(String familyCode) {
		// TODO Return a collection of devices of the specified family code
		return null;
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
			msg = ownet.Read(path);
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

	private void reset() {
		this.deviceIdCache = new ArrayList<String>(5);
	}

	protected String getDevicePath(String id, String[] in) {
		ArrayList<String> paths = new ArrayList<String>(5);
		String path = null;
		for (String i : in) {
			String[] str = this.dir(i);
			for (String s : str) {
				paths.add(s);
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
			path = getDevicePath(id, str);
			if (path != null) {
				return path;
			}
		}

		return path;
	}

	public void print() {
		if (deviceIdCache != null) {
			if (!deviceIdCache.isEmpty()) {
				reset();
			}
		} else {
			reset();
		}

		String[] str = { "/" };
		print(str);
	}

	protected synchronized void print(String[] in) {
		ArrayList<String> paths = new ArrayList<String>(5);

		for (String i : in) {
			String[] str = this.dir(i);
			for (String s : str) {
				paths.add(s);
			}
		}

		ArrayList<String> next = new ArrayList<String>(10);

		for (String p : paths) {
			String id = p.substring(p.lastIndexOf("/") + 1);
			if (id.startsWith("10.") || id.startsWith("26.")
					|| id.startsWith("81.")) {
				if (!deviceIdCache.contains(id)) {
					System.out.println(p);
					deviceIdCache.add(id);
				}
			}
			if (id.startsWith("1F")) {
				if (!deviceIdCache.contains(id)) {
					deviceIdCache.add(id);
					System.out.println(p);
					next.add(p + "/main/");
				}

			}
		}
		if (!next.isEmpty()) {
			String str[] = new String[next.size()];
			next.toArray(str);
			print(str);
		}
	}

	public static void main(String[] args) {
		OwClient owc = null;
		final OptionParser parser = new OptionParser();
		OptionSpec<String> host = parser.acceptsAll(asList("h", "host"),
				"Address of owserver.").withRequiredArg().ofType(String.class);
		OptionSpec<Integer> port = parser.acceptsAll(asList("p", "port"),
				"Port of owserver. Defaults to port 4304").withRequiredArg()
				.ofType(Integer.class);
		OptionSpec<String> id = parser.acceptsAll(asList("i", "id"),
				"ID of 1-wire device to read.").withRequiredArg().ofType(
				String.class);
		parser.acceptsAll(asList("temp", "temperature"),
				"Output temperature reading of specified device");
		parser.acceptsAll(asList("hum", "humidity"),
				"Output humidity reading of specified device");
		parser.acceptsAll(asList("a", "all"),
				"Print values of all devices attached to 1-wire bus.");
		parser.acceptsAll(asList("?", "help"), "Shows help");

		OptionSet options = parser.parse(args);

		if (options.has("?")) {
			try {
				parser.printHelpOn(System.out);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.exit(0);
			}
		}

		if (options.has(host)) {
			if (options.has(port)) {
				OWNet ownet = new OWNet(options.valueOf(host), options
						.valueOf(port));
				owc = new OwClient(ownet);
			} else {
				OWNet ownet = new OWNet(options.valueOf(host), 4304);
				owc = new OwClient(ownet);
			}
		} else {
			if (options.has("port")) {
				OWNet ownet = new OWNet("127.0.0.1", options.valueOf(port));
				owc = new OwClient(ownet);
			} else {
				OWNet ownet = new OWNet("127.0.0.1", 4304);
				owc = new OwClient(ownet);
			}
		}

		if (options.has("id")) {
			OwDevice device = owc.find(options.valueOf(id));
			if (options.has("temperature")) {
				if (device.getClass() == DS18S20.class) {
					System.out.print(((DS18S20) device).getTemperature());
				}
				if (device.getClass() == DS2438.class) {
					System.out.print(((DS2438) device).getTemperature());
				}
			}

			if (options.has("humidity")) {
				if (device.getClass() == DS2438.class) {
					System.out.print(((DS2438) device).getHumidity());
				}
			}

		}
	}
}
