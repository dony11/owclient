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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;
import net.strandbygaard.onewire.device.DS18S20;
import net.strandbygaard.onewire.device.OwId;
import net.strandbygaard.onewire.device.OwPath;
import net.strandbygaard.onewire.device.OwSensor;
import net.strandbygaard.onewire.device.OwSensor.Reading;
import net.strandbygaard.onewire.owclient.OwClient;

public class DS18S20Test extends TestCase {

	private OwSensor owd;
	private OwClient owc;

	private static final String DEVICEID = "10.16438A010800";
	private static final String DEVICEPATH = "/1F.16E103000000/main/10.16438A010800";

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		owc = mock(OwClient.class);
		when(owc.read(DEVICEPATH + DS18S20.TEMPERATURE)).thenReturn("20.0");

		owd = new DS18S20(DEVICEPATH, owc);
	}

	public void testCanUpdate() {
		assertEquals(true, owd.canUpdate());
	}

	public void testGetId1() {
		OwId expected = new OwId(DEVICEID);
		OwId actual = owd.getId();
		assertEquals(expected.getId(), actual.getId());
	}

	public void testGetId2() {
		owd = new DS18S20(DEVICEPATH + "/", owc);
		OwId expected = new OwId(DEVICEID);
		OwId actual = owd.getId();
		assertEquals(expected.getId(), actual.getId());
	}

	public void testGetPath() {
		OwPath expected = new OwPath(DEVICEPATH);
		OwPath actual = owd.getPath();
		assertEquals(expected.toString(), actual.toString());
	}

	public void testCanRead1() {
		boolean expected = true;
		boolean actual = owd.canRead(Reading.TEMP);
		assertEquals(expected, actual);
	}

	public void testCanRead2() {
		boolean expected = false;
		boolean actual = owd.canRead(Reading.HUM);
		assertEquals(expected, actual);
	}

	public void testRead() {
		double expected = 20.0;
		double actual = owd.read(Reading.TEMP);
		assertEquals(expected, actual);
	}

	public void testGetUnit() {
		String expected = "C";
		String actual = ((DS18S20) owd).getUnit(Reading.TEMP);
		assertEquals(expected, actual);
	}

	public void testToString() {
		String expected = "20.0C";
		String actual = owd.toString();
		assertEquals(expected, actual);
	}
}
