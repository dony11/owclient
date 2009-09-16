package net.strandbygaard.onewire.owclient;

import net.strandbygaard.onewire.device.DS18S20;
import net.strandbygaard.onewire.device.DS2438;

public class OwDeviceFactory {

	public static OwDevice createDevice(String id) {
		OwDevice dev = null;
		dev = construct(id);
		return dev;
	}

	private static OwDevice construct(String id) {
		OwDevice dev = null;
		if (id.startsWith("26.")) {
			dev = new DS2438(id);
		}
		if (id.startsWith("10.")) {
			dev = new DS18S20(id);
		}
		return dev;
	}
}
