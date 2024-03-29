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
 * This class represents a 1-wire device of type DS18S20.
 * </p>
 * 
 * <p>
 * The DS18S20 is a high precision thermometer contained in a small 3 connector
 * package. The device is most common 1-wire device encountered, and many other
 * device types incorporates a DS18S20 (in parallel) because of it's small size,
 * and the usefulness of having a temperature reading available.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 */
public class DS18S20 extends OwSensorImpl {

	public static final String TEMPERATURE = "/temperature";
	private double temperature;

	public DS18S20(OwPath path, OwClient owc) {
		super(path, owc);
	}

	public DS18S20(String path, OwClient owc) {
		super(path, owc);
	}

	public boolean canRead(Reading r) {
		boolean canRead = true;
		if (r != Reading.TEMP) {
			canRead = false;
		}
		return canRead;
	}

	@Override
	public double read(Reading r) {
		update();

		// FIXME Returning temperature for any Reading r is not right.
		return temperature;
	}

	@Override
	public String getUnit(Reading r) {
		String unit = "";
		if (r == Reading.TEMP) {
			unit = "C";
		}
		return unit;
	}

	@Override
	public void update() {
		if (canUpdate()) {
			if (shouldUpdate()) {
				setTemperature(Double.valueOf(owc.read(path + TEMPERATURE)));
				super.update();
			}
		}
	}

	private void setTemperature(double temp) {
		temperature = temp;
	}

	@Override
	public String toString() {
		return String.valueOf(read(Reading.TEMP)) + getUnit(Reading.TEMP);
	}
}
