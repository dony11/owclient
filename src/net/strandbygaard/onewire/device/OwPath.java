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
 * This class represents a the fully qualified path of a 1-wire device. <br/>
 * </p>
 * 
 * <p>
 * The path is of the form: <code>/path/to/device</code> where
 * <code>device</code> is always the ID of the device.<br/>
 * </p>
 * 
 * <p>
 * This implementation includes methods to support common actions, such as
 * getting the ID from the full path.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 */
public class OwPath extends OwItem {

	/**
	 * <p>
	 * The constructor takes a <code>String</code> with the path to encapsulate,
	 * and instantiates the <code>OwItem</code> parent class with this value.
	 * </p>
	 * 
	 * <p>
	 * The constructor converts the passed <code>String</code> to upper case,
	 * and strips any trailing "/" (after <code>super()</code>) is called to
	 * ensure it has been white space trimmed first.
	 * </p>
	 * 
	 * @param path
	 *            - a <code>String</code> with the path to encapsulate.
	 */
	public OwPath(String path) {
		super(path);
		super.item.toUpperCase();

		if (item.endsWith("/")) {
			item = item.substring(0, item.length() - 1);
		}

		super.item = item;
	}

	/**
	 * <p>
	 * Get a string with the path
	 * </p>
	 * 
	 * @return a <code>String</code> with the path.
	 */
	public String getPath() {
		return super.item;
	}

	/**
	 * <p>
	 * Get the unique ID from the path to a 1-wire device
	 * </p>
	 * 
	 * @return <code>OwId</code> representing the ID element of the path pointed
	 *         to by this <code>OwPath</code> object
	 */
	public OwId getIdFromPath() {
		OwId owid;
		if (item != "") {
			owid = new OwId(item.substring(item.lastIndexOf("/") + 1));
		} else {
			owid = new OwId("");
		}
		return owid;
	}
}
