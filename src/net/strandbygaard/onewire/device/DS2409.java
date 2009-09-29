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
 * This class represents a 1-wire device of type DS2409.
 * </p>
 * 
 * <p>
 * The DS2409 is a microlan coupler that can be used to multiplex two 1-wire
 * busses onto one, effectively working as a two port 1-wire hub. A common
 * design incorporates 3 of these devices into a 6 port 1-wire hub.
 * </p>
 * 
 * <p>
 * There are many variants of these 6 port hubs available. Most designs are
 * based on <a
 * href="http://www.simat.org.uk/1wirehub.html">http://www.simat.org.
 * uk/1wirehub.html</a>. This implementation has only been tested with the Hobby
 * Boards design.
 * </p>
 * 
 * @author Martin Strandbygaard
 * 
 */
public class DS2409 extends OwDeviceImpl {

	public static final String ID = "/id";
	public static final String ADDRESS = "/address";

	public DS2409(OwPath path, OwClient owc) {
		super(path, owc);
	}

	public DS2409(String path, OwClient owc) {
		super(path, owc);
	}

	@Override
	public String toString() {
		return id.getId();
	}
}
