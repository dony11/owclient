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

/**
 * <p>
 * This interface is implemented by any class representing a 1-wire device that
 * is a sensor.
 * </p>
 * 
 * <p>
 * A sensor is defined as any 1-wire device that records one or more values
 * about its surroundings, and which can be read from the device over a 1-wire
 * bus.
 * </p>
 * 
 * <p>
 * This interface only facilitates one-way communication with a 1-wire device.
 * This interface does not preclude a device supporting 1-wire put operations,
 * but a differenc interface should be implemented to support this.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 */
public interface OwSensor extends OwDevice {

	// TODO Refactor this.
	public static enum Reading {
		TEMP, HUM
	};

	// TODO Refactor this.
	public static enum Unit {
		TEMP, HUM
	};

	/**
	 * <p>
	 * Checks if a specific implementation can read sensor values of a specific
	 * type.
	 * </p>
	 * 
	 * <p>
	 * Some sensors register multiple values, and consumers can use this method
	 * to find out which readings are supported by an implementation.
	 * </p>
	 * 
	 * @param r
	 *            - the <code>Reading</code> to check if is supported by this
	 *            implementation.
	 * @return <code>true</code> - if the implementation supports a reading
	 *         identified by <code>Reading</code>. <code>false</code> if not.
	 */
	boolean canRead(Reading r);

	/**
	 * <p>
	 * Reads the current value of type <code>Reading</code> if it is supported.
	 * </p>
	 * 
	 * @param r
	 *            - the type of <code>Reading</code> to read from the sensor
	 * @return <code>double</code> - the value of the device, if the
	 *         <code>Reading</code> specified is supported.
	 */
	double read(Reading r);

	/**
	 * <p>
	 * Gets a <code>String</code> with the current unit of a
	 * <code>Reading</code>
	 * </p>
	 * 
	 * @param r
	 *            - type of <code>Reading</code> to get the current unit for.
	 * @return <codeString</code> with the unit representing the
	 *         <code>Reading</code>
	 */
	public String getUnit(Reading r);

	/**
	 * <p>
	 * Checks if a sensor implementation is updateable.
	 * </p>
	 * 
	 * <p>
	 * If <code>OwClient</code> is used in non-caching mode, this method should
	 * be called at least once on a device, to make sure the device can actually
	 * be updated, before <code>read()</code> or <code>update()</code> are
	 * called, to avoid unnecessary [1-wire] bus activity.
	 * </p>
	 * 
	 * @return <code>true</code> if it is updateable. <code>false</code> if not.
	 */
	boolean canUpdate();

	/**
	 * <p>
	 * Updates the current value of this sensor.
	 * </p>
	 */
	public void update();

	/**
	 * <p>
	 * Gets the time when the value of this sensor was last updated.
	 * </p>
	 * 
	 * <p>
	 * If <code>OwClient</code> is used in non-caching mode, this method can be
	 * used to decide, if it is necessary to update the value of this device. In
	 * caching mode, this method is used to determine if the current value needs
	 * to be updated. In caching mode this decision is based on a configurable
	 * delay interval.
	 * </p>
	 * 
	 * @return <code>true</code> if it is updateable. <code>false</code> if not.
	 */
	long getLastUpdate();

}
