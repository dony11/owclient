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
import net.strandbygaard.onewire.owclient.OwDevice;

public class OwDeviceImpl implements OwDevice {

	private String id;
	private OwId owId;
	private String path;
	private OwPath owPath;
	private OwClient bus;

	public OwDeviceImpl(String path) {
		this.path = path;
		this.id = id;
		
		this.owId = new OwId(read(path+"/id"));
		this.owPath = new OwPath(path);
	}

	protected String read(String path)
	{
		return bus.read(path);
	}

	public String getId() {
		return id;
	}


	public String getPath() {
		return null;
	}

	public String[] getValuePaths() {
		return null;
	}
}
