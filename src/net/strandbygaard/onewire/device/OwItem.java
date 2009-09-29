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
 * Parent class used to sub class to represent specific 1-wire attributes of a
 * 1-wire device.
 * 
 * Any sub class of this class should adhere to the pattern of only
 * encapsulating one property, and use this class as super class to store that
 * property.
 * 
 * This class encapsulates a white space trimmed string property and ensures
 * non-nullability [of this property]. Specific attribute implementations made
 * add additional logic.
 * 
 * @author Martin Strandbygaard
 * 
 */
public class OwItem {

	/*
	 * The property encapsulated by this class
	 */
	protected String item;

	OwItem(String item) {
		if (item == null) {
			this.item = "";
		} else {
			this.item = item.trim();
		}
	}

	@Override
	public String toString() {
		return item;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}

		// object must be OwItem at this point
		return item.equalsIgnoreCase(((OwItem) o).toString());
	}
}
