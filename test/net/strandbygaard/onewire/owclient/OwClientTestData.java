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

public class OwClientTestData {

	final static int TOTALDEVICES = 2;

	final static String[] rootPaths = { "/1F.16E103000000", "/1F.A3E903000000",
			"/1F.AFE103000000", "/bus.0", "/uncached", "/settings", "/system",
			"/statistics", "/structure" };

	/*
	 * /1F.16E103000000/address /1F.16E103000000/aux
	 * /1F.16E103000000/branch.BYTE /1F.16E103000000/branch.ALL
	 * /1F.16E103000000/branch.0 /1F.16E103000000/branch.1
	 * /1F.16E103000000/clearevent /1F.16E103000000/control
	 * /1F.16E103000000/crc8 /1F.16E103000000/discharge
	 * /1F.16E103000000/event.BYTE /1F.16E103000000/event.ALL
	 * /1F.16E103000000/event.0 /1F.16E103000000/event.1 /1F.16E103000000/family
	 * /1F.16E103000000/id /1F.16E103000000/locator /1F.16E103000000/main
	 * /1F.16E103000000/present /1F.16E103000000/r_address /1F.16E103000000/r_id
	 * /1F.16E103000000/r_locator /1F.16E103000000/sensed.BYTE
	 * /1F.16E103000000/sensed.ALL /1F.16E103000000/sensed.0
	 * /1F.16E103000000/sensed.1 /1F.16E103000000/type
	 */
	final static String[] portLocalPaths = { "/1F.16E103000000/address",
			"/1F.16E103000000/id", "/1F.16E103000000/main" };

	final static String[] portAttachedPaths = {
			"/1F.16E103000000/main/10.16438A010800",
			"/1F.16E103000000/main/26.373BB6000000",
			"/1F.16E103000000/main/81.851428000000",
			"/1F.16E103000000/main/1F.16E103000000",
			"/1F.16E103000000/main/1F.A3E903000000",
			"/1F.16E103000000/main/1F.AFE103000000",
			"/1F.16E103000000/main/simultaneous", "/1F.16E103000000/main/alarm" };

	/*
	 * /1F.16E103000000/main/26.373BB6000000/CA
	 * /1F.16E103000000/main/26.373BB6000000/EE
	 * /1F.16E103000000/main/26.373BB6000000/HIH4000
	 * /1F.16E103000000/main/26.373BB6000000/HTM1735
	 * /1F.16E103000000/main/26.373BB6000000/IAD
	 * /1F.16E103000000/main/26.373BB6000000/MultiSensor
	 * /1F.16E103000000/main/26.373BB6000000/VAD
	 * /1F.16E103000000/main/26.373BB6000000/VDD
	 * /1F.16E103000000/main/26.373BB6000000/address
	 * /1F.16E103000000/main/26.373BB6000000/crc8
	 * /1F.16E103000000/main/26.373BB6000000/date
	 * /1F.16E103000000/main/26.373BB6000000/disconnect
	 * /1F.16E103000000/main/26.373BB6000000/endcharge
	 * /1F.16E103000000/main/26.373BB6000000/family
	 * /1F.16E103000000/main/26.373BB6000000/humidity
	 * /1F.16E103000000/main/26.373BB6000000/id
	 * /1F.16E103000000/main/26.373BB6000000/locator
	 * /1F.16E103000000/main/26.373BB6000000/offset
	 * /1F.16E103000000/main/26.373BB6000000/pages
	 * /1F.16E103000000/main/26.373BB6000000/present
	 * /1F.16E103000000/main/26.373BB6000000/r_address
	 * /1F.16E103000000/main/26.373BB6000000/r_id
	 * /1F.16E103000000/main/26.373BB6000000/r_locator
	 * /1F.16E103000000/main/26.373BB6000000/temperature
	 * /1F.16E103000000/main/26.373BB6000000/type
	 * /1F.16E103000000/main/26.373BB6000000/udate
	 * /1F.16E103000000/main/26.373BB6000000/vis
	 */
	final static String[] sensorDS2438Reading = {
			"/1F.16E103000000/main/26.373BB6000000/humidity",
			"/1F.16E103000000/main/26.373BB6000000/temperature",
			"/1F.16E103000000/main/26.373BB6000000/address" };

	/*
	 * /1F.16E103000000/main/10.16438A010800/address
	 * /1F.16E103000000/main/10.16438A010800/crc8
	 * /1F.16E103000000/main/10.16438A010800/die
	 * /1F.16E103000000/main/10.16438A010800/family
	 * /1F.16E103000000/main/10.16438A010800/id
	 * /1F.16E103000000/main/10.16438A010800/locator
	 * /1F.16E103000000/main/10.16438A010800/power
	 * /1F.16E103000000/main/10.16438A010800/present
	 * /1F.16E103000000/main/10.16438A010800/r_address
	 * /1F.16E103000000/main/10.16438A010800/r_id
	 * /1F.16E103000000/main/10.16438A010800/r_locator
	 * /1F.16E103000000/main/10.16438A010800/temperature
	 * /1F.16E103000000/main/10.16438A010800/temphigh
	 * /1F.16E103000000/main/10.16438A010800/templow
	 * /1F.16E103000000/main/10.16438A010800/trim
	 * /1F.16E103000000/main/10.16438A010800/trimblanket
	 * /1F.16E103000000/main/10.16438A010800/trimvalid
	 * /1F.16E103000000/main/10.16438A010800/type
	 */
	final static String[] sensorDS18S20Reading = {
			"/1F.16E103000000/main/10.16438A010800/temperature",
			"/1F.16E103000000/main/10.16438A010800/address" };

}
