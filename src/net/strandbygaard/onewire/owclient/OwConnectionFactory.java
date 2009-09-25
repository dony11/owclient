/**
 * Module: owbus/java
 * Package: net.strandbygaard.onewire.owbus.OwConnectionFactory
 *  
 * @author  Martin Strandbygaard (martin@strandbygaard.net)
 * @version $Id:
 * 
 * Copyright (C) 2009 Martin Strandbygaard
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.strandbygaard.onewire.owclient;

import org.owfs.ownet.*;

public class OwConnectionFactory {

	private static OWNet ownet = null;

	private static String host = null;
	private static int port = 0;
	
	private static boolean isInitialized = false;

	public synchronized static OWNet getConnection()
			throws IllegalStateException {
		if (ownet == null) {
			if (host == null) {
				ownet = new OWNet();
			} else {
				ownet = new OWNet(host, port);
			}
			isInitialized = true;
		}
		ownet.setFormatflags(OWNet.OWNET_FLAG_T_C);
		return ownet;
	}

	public synchronized static void init(String h, int p) {
		if (!isInitialized) {
			host = h;
			port = p;
			if (!isInitialized) {
				isInitialized = true;
			}
		}
	}

	public static String getHost() {
		return host;
	}

	public static int getPort() {
		return port;
	}
}
