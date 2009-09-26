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
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.strandbygaard.onewire.owclient.OwSensor.Reading;

import org.owfs.ownet.OWNet;

public class OwClientCLI {

	private long start = 0;
	private long stop = 0;
	protected String[] args;

	public OwClientCLI(String[] args) {
		this.args = args;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OwClientCLI owc = new OwClientCLI(args);
		owc.process();
	}

	public void printRuntime() {
		System.out.println("Operation completed in: "
				+ (((((double) stop) - ((double) start)) / 1000)) + "s");
	}

	public void process() {
		this.startTimer();
		OwClient owc = null;
		OWNet ownet;

		final OptionParser parser = new OptionParser();
		OptionSpec<String> host = parser.acceptsAll(asList("h", "host"),
				"Address of owserver.").withRequiredArg().ofType(String.class);
		OptionSpec<Integer> port = parser.acceptsAll(asList("p", "port"),
				"Port of owserver. Defaults to port 4304").withRequiredArg()
				.ofType(Integer.class);
		OptionSpec<String> id = parser.acceptsAll(asList("i", "id"),
				"ID of 1-wire device to read.").withRequiredArg().ofType(
				String.class);
		OptionSpec<String> fam = parser.acceptsAll(asList("fam", "family"),
				"Family of 1-wire devices to read.").withRequiredArg().ofType(
				String.class);
		parser.acceptsAll(asList("temp", "temperature"),
				"Output temperature reading of specified device");
		parser.acceptsAll(asList("time"), "Output runtime of command");
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
				ownet = new OWNet(options.valueOf(host), options.valueOf(port));
				owc = new OwClient(ownet);
			} else {
				ownet = new OWNet(options.valueOf(host), 4304);
				owc = new OwClient(ownet);
			}
		} else {
			if (options.has("port")) {
				ownet = new OWNet("127.0.0.1", options.valueOf(port));
				owc = new OwClient(ownet);
			} else {
				ownet = new OWNet("127.0.0.1", 4304);
				owc = new OwClient(ownet);
			}
		}

		if (options.has("id")) {
			try {
				OwDevice device = owc.find(options.valueOf(id));
				if (options.has("temperature")) {
					if (device.getClass() == DS18S20.class) {
						System.out.print(((DS18S20) device).read(Reading.TEMP));
					}
					if (device.getClass() == DS2438.class) {
						System.out.print(((DS2438) device).read(Reading.TEMP));
					}
				}

				if (options.has("humidity")) {
					if (device.getClass() == DS2438.class) {
						System.out.print(((DS2438) device).read(Reading.HUM));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedDeviceException e) {
				e.printStackTrace();
			}
			return;
		}
		if (options.has("all")) {
			List<OwSensor> devices = owc.list();
			for (OwSensor owd : devices) {
				System.out.print(owd.getId() + ": ");
				System.out.println(owd.toString());
			}
		}

		if (options.has("fam")) {
			List<OwSensor> devices;
			try {
				devices = owc.list(options.valueOf(fam));
				for (OwSensor owd : devices) {
					System.out.print(owd.getId() + ": ");
					System.out.println(owd.toString());
				}
			} catch (UnsupportedDeviceException e) {
				e.printStackTrace();
			}
		}

		this.stopTimer();
		if (options.has("time")) {
			this.printRuntime();
		}
	}

	private void startTimer() {
		start = System.currentTimeMillis();
	}

	private void stopTimer() {
		stop = System.currentTimeMillis();
	}
}
