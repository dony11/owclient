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
 * Interface to a 1-wire device.
 * </p>
 * 
 * <p>
 * This is the most generalized interface representing 1-wire device. Any 1-wire
 * device regardless of type and purpose, can be accessed using this interface.
 * </p>
 * 
 * <p>
 * The interface exposes methods to get the unique ID of a 1-wire device, and
 * the fully qualified path to the device on a 1-wire bus.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 * 
 */
public interface OwDevice {

	/**
	 * A list of all 1-wire family codes supported by this implementation.
	 */
	// TODO Refactor this implementation
	public static final String[] supportedFamilyCodes = { "10", "26" };

	/**
	 * 
	 * @return <code>String</code> id - The unique ID of this 1-wire device
	 */
	OwId getId();

	/**
	 * 
	 * @return <code>String</code> path - The path (starting from /) to this
	 *         1-wire device
	 */
	OwPath getPath();

}
