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

public class DS2438 extends OwDeviceImpl {

	public static final String TEMPERATURE = "/temperature";
	public static final String HUMIDITY = "/humidity";
	private double temperature;
	private double humidity;

	public DS2438(String path, OwClient owc) {
		super(path, owc);
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
