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

public class OwId extends OwItem {

	public OwId(String id) {
		super(id);
		super.item.toUpperCase();
	}

	public String getId() {
		return super.item;
	}

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
