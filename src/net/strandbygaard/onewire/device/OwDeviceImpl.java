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

public class OwDeviceImpl implements OwDevice {

	public static final long UPDATE_INTERVAL = 1;

	protected OwId id;
	protected OwPath path;
	protected long lastUpdate;
	protected OwClient owc;

	public OwDeviceImpl(OwPath path, OwClient owc) {
		if (path == null) {
			path = new OwPath("");
		}

		if (owc == null) {
			throw new IllegalArgumentException("OwClient cannot be null");
		}

		this.path = path;
		this.owc = owc;
		this.id = path.getIdFromPath();
	}

	public OwDeviceImpl(String path, OwClient owc) {
		this(new OwPath(path), owc);
	}

	public OwDeviceImpl(String path) {
		if (path == null) {
			path = "";
		}
		
		this.path = new OwPath(path);
		this.id = this.path.getIdFromPath();
	}

	public OwId getId() {
		return id;
	}

	public OwPath getPath() {
		return path;
	}

	@Override
	public String toString() {
		return id.getId();
	}
}
