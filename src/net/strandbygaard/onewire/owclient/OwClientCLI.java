package net.strandbygaard.onewire.owclient;

import static java.util.Arrays.asList;

import java.io.IOException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.owfs.ownet.OWNet;

public class OwClientCLI {

	/**
	 * @param args
	 */
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

		OWNet ownet;
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

		String[] str = owc.dir("/1F.16E103000000/main/26.373BB6000000");
		for (String s : str) {
			System.out.println(s);
		}

		if (options.has("id")) {
			try {
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
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedDeviceException e) {
				e.printStackTrace();
			}
		}
	}
}
