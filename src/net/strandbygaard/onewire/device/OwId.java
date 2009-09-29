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
 * This class represents a unique 1-wire ID.
 * 
 * It adds additional methods, to support common actions on an ID, such as
 * getting it's family code, and testing for membership [of a specific family
 * code].
 * 
 * @author Martin Strandbygaard
 * 
 */
public class OwId extends OwItem {

	public OwId(String id) {
		super(id);

		/*
		 * Enforces ID string formatting as upper case. It is often relevant to
		 * compare two ID's, and the common formatting rule is white space
		 * trimmed uppercase.
		 */
		super.item.toUpperCase();
	}

	/**
	 * Getter for the ID represented by this class
	 * 
	 * @return a <code>string</code> with the unique ID
	 */
	public String getId() {
		return super.item;
	}

	/**
	 * Gets the family code part of this unique 1-wire ID.
	 * 
	 * The first two digits in a 1-wire ID represents the family code of the
	 * device. The family code determines the functionality of a 1-wire device,
	 * and each device type has a specific implementation supporting the device
	 * types' specific features/functionality.
	 * 
	 * @return the family code of this unique 1-wire ID, if the ID is valid, and
	 *         an empty string if not.
	 */
	public String getFamilyCode() {
		if (item == "") {
			return "";
		}

		if (item.length() < 2) {
			return "";
		}

		return item.substring(0, 2);
	}

	/**
	 * Checks if this ID is in a given family code
	 * 
	 * @param code
	 *            - The code to check to see if this ID belongs to
	 * @return <code>true</code> if this ID is the specified family code.
	 *         <code>false</code> otherwise.
	 */
	public boolean isFamilyCode(String code) {
		boolean isCode = false;

		if (code != null) {
			if (this.getFamilyCode().equalsIgnoreCase(code)) {
				isCode = true;
			}
		}

		return isCode;
	}

}
