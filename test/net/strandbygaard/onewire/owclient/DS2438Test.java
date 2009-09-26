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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.strandbygaard.onewire.owclient.OwDevice.Reading;
import junit.framework.TestCase;

public class DS2438Test extends TestCase {

	private OwDevice owd;
	private OwClient owc;

	private static final String DEVICEID = "26.373BB6000000";
	private static final String DEVICEPATH = "/1F.16E103000000/main/26.373BB6000000";

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		owc = mock(OwClient.class);
		when(owc.read(DEVICEPATH + DS2438.TEMPERATURE)).thenReturn("20.0");
		when(owc.read(DEVICEPATH + DS2438.HUMIDITY)).thenReturn("60.0");

		owd = new DS2438(DEVICEPATH, owc);

	}

	public void testCanUpdate() {
		assertEquals(true, owd.canUpdate());
	}

	public void testGetId1() {
		String expected = DEVICEID;
		String actual = owd.getId();
		assertEquals(expected, actual);
	}

	public void testGetId2() {
		owd = new DS2438(DEVICEPATH + "/", owc);
		String expected = DEVICEID;
		String actual = owd.getId();
		assertEquals(expected, actual);
	}

	public void testGetPath() {
		String expected = DEVICEPATH;
		String actual = owd.getPath();
		assertEquals(expected, actual);
	}

	public void testRead1() {
		double expected = 20.0;
		double actual = owd.read(Reading.TEMP);
		assertEquals(expected, actual);
	}

	public void testRead2() {
		double expected = 60.0;
		double actual = owd.read(Reading.HUM);
		assertEquals(expected, actual);
	}

	public void testGetUnit1() {
		String expected = "C";
		String actual = ((DS2438) owd).getUnit(Reading.TEMP);
		assertEquals(expected, actual);
	}

	public void testGetUnit2() {
		String expected = "%";
		String actual = ((DS2438) owd).getUnit(Reading.HUM);
		assertEquals(expected, actual);
	}

	public void testToString() {
		String expected = "20.0C 60.0%";
		String actual = owd.toString();
		assertEquals(expected, actual);
	}
}
