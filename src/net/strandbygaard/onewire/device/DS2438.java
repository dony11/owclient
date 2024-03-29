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

package net.strandbygaard.onewire.device;

import net.strandbygaard.onewire.owclient.OwClient;

/**
 * <p>
 * This class represents a 1-wire device of type DS2438.
 * </p>
 * 
 * <p>
 * The DS2438 battery monitor chip that is often used in a a combined
 * thermometer and humidity sensor.
 * </p>
 * 
 * <p>
 * This class was developed for a device similar to Hobby Boards HT3-R1-A
 * (combined thermometer and humidity sensor), and has only been tested with a
 * Hobby Boards HTS3-R1-A, however similar devices should also work.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 */
public class DS2438 extends OwSensorImpl {

	public static final String TEMPERATURE = "/temperature";
	public static final String HUMIDITY = "/humidity";
	private double temperature;
	private double humidity;

	public DS2438(OwPath path, OwClient owc) {
		super(path, owc);
	}

	public DS2438(String path, OwClient owc) {
		super(path, owc);
	}

	public boolean canRead(Reading r) {
		boolean canRead = true;
		if (r != Reading.TEMP && r != Reading.HUM) {
			canRead = false;
		}
		return canRead;
	}

	@Override
	public double read(Reading r) {
		update();
		double val = 0;
		if (r == Reading.TEMP) {
			val = temperature;
		}
		if (r == Reading.HUM) {
			val = humidity;
		}
		return val;
	}

	@Override
	public String getUnit(Reading r) {
		String unit = "";
		if (r == Reading.TEMP) {
			unit = "C";
		}
		if (r == Reading.HUM) {
			unit = "%";
		}
		return unit;
	}

	@Override
	public void update() {
		if (canUpdate()) {
			if (shouldUpdate()) {
				setTemperature(Double.valueOf(owc.read(path + TEMPERATURE)));
				setHumidity(Double.valueOf(owc.read(path + HUMIDITY)));
				super.update();
			}
		}
	}

	private void setTemperature(double temp) {
		temperature = temp;
	}

	private void setHumidity(double hum) {
		humidity = hum;
	}

	@Override
	public String toString() {
		return String.valueOf(read(Reading.TEMP)) + getUnit(Reading.TEMP) + " "
				+ String.valueOf(read(Reading.HUM)) + getUnit(Reading.HUM);
	}
}
