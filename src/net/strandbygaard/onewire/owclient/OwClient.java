/**
 * Module: owbus/java
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

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.owfs.ownet.OWNet;

public class OwClient {

	OWNet ownet = null;

	public OwClient() {
		if (ownet == null) {
			OwConnectionFactory.init();
			ownet = OwConnectionFactory.getConnection();
		}
	}

	public OwClient(String host, int port) {
		if (ownet == null) {
			OwConnectionFactory.init(host, port);
			ownet = OwConnectionFactory.getConnection();
		}
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
			msg = ownet.safeRead(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ownet.Disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	
		return msg;
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
				owc = new OwClient(options.valueOf(host), options.valueOf(port));
			} else {
				owc = new OwClient((String) options.valueOf(host), 4304);
			}
		} else {
			if (options.has("port")) {
				owc = new OwClient("127.0.0.1", options.valueOf(port));
			} else {
				owc = new OwClient();
			}
		}

		OwTree owt = new OwTree(owc);
		// if (options.has("id")) {
		// OwDevice device = OwDeviceFactory.createDevice("id");
		// String[] paths = device.getValuePaths();
		// for (String p : paths) {
		// System.out.println(owc.read(p));
		// }
		// } else {
		//
		// }

		owt.print();

	}

	private static OptionSet getOptions(String[] args) {
		final OptionParser parser = new OptionParser();
		OptionSpec<String> host = parser.acceptsAll(asList("h", "host"),
				"Address of owserver.").withRequiredArg().ofType(String.class);
		OptionSpec<Integer> port = parser.acceptsAll(asList("p", "port"),
				"Port of owserver. Defaults to port 4304").withRequiredArg()
				.ofType(Integer.class);
		OptionSpec<String> id = parser.acceptsAll(asList("i", "id"),
				"ID of 1-wire device to read.").withRequiredArg().ofType(
				String.class);
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

		return options;
	}
}
