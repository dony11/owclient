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

public abstract class OwDeviceImpl implements OwDevice {

	public static final long UPDATE_INTERVAL = 1;

	protected String id;
	protected String path;
	protected long lastUpdate;
	protected OwClient owc;

	public OwDeviceImpl(String path, OwClient owc) {
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		this.path = path;
		this.owc = owc;
		this.id = path.substring(path.lastIndexOf("/") + 1);
	}

	public OwDeviceImpl(String path) {
		this.path = path;
		this.id = path.substring(path.lastIndexOf("/") + 1);
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public long getLastUpdate() {
		return 0;
	}

	public boolean canUpdate() {
		boolean state = false;
		if (owc != null) {
			state = true;
		}
		return state;
	}

	protected boolean shouldUpdate() {
		boolean state = false;
		long now = System.currentTimeMillis();
		if ((now - lastUpdate) > UPDATE_INTERVAL) {
			state = true;
		}
		return state;
	}

	public void update() {
		lastUpdate = System.currentTimeMillis();
	}

	public abstract double read(Reading r);

	@Override
	public String toString() {
		return this.id;
	}
}
