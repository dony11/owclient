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

public abstract class OwSensorImpl extends OwDeviceImpl implements OwSensor {

	public OwSensorImpl(OwPath path, OwClient owc) {
		super(path, owc);
	}

	public OwSensorImpl(String path, OwClient owc) {
		super(path, owc);
	}

	public OwSensorImpl(String path) {
		super(path);
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

	public abstract String getUnit(Reading r);
}
