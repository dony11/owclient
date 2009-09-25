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

import java.util.List;

import junit.framework.TestCase;

import org.owfs.ownet.OWNet;

public class OwClientTest extends TestCase {

	private OWNet mockedOWNet;
	private OwClient owc;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		mockedOWNet = mock(OWNet.class);
		when(mockedOWNet.Dir("/")).thenReturn(OwClientTestData.rootPaths);
		when(mockedOWNet.DirAll("/")).thenReturn(OwClientTestData.rootPaths);

		when(mockedOWNet.DirAll("/1F.16E103000000")).thenReturn(
				OwClientTestData.portLocalPaths);
		when(mockedOWNet.DirAll("/1F.16E103000000/")).thenReturn(
				OwClientTestData.portLocalPaths);

		when(mockedOWNet.DirAll("/1F.16E103000000/main")).thenReturn(
				OwClientTestData.portAttachedPaths);
		when(mockedOWNet.DirAll("/1F.16E103000000/main/")).thenReturn(
				OwClientTestData.portAttachedPaths);
		when(
				mockedOWNet
						.Read("/1F.16E103000000/main/10.16438A010800/temperature"))
				.thenReturn("20");

		owc = new OwClient(mockedOWNet);
	}

	public void testFindAttachedDevice1() throws Exception {
		assertNotNull(owc.find("10.16438A010800"));
	}

	public void testFindAttachedDevice2() throws Exception {
		assertNotNull(owc.find("26.373BB6000000"));
	}

	public void testFindAttachedDevice3() throws Exception {
		assertNotNull(owc.find("81.851428000000"));
	}

	public void testFindAttachedDevice4() throws Exception {
		assertNotNull(owc.find("1F.16E103000000"));
	}

	public void testFindNotAttachedDevice1() throws IllegalArgumentException,
			UnsupportedDeviceException {
		OwDevice owd = null;
		try {
			owd = owc.find("10.xxxxxxxxxxxx");
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
		}
		assertNull(owd);
	}

	public void testFindNotAttachedDevice2() throws IllegalArgumentException,
			UnsupportedDeviceException {
		OwDevice owd = null;
		try {
			owd = owc.find("10");
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
		}
		assertNull(owd);
	}

	public void testFindNotAttachedDevice3() throws IllegalArgumentException,
			UnsupportedDeviceException {
		OwDevice owd = null;
		try {
			owd = owc.find("10.");
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
		}
		assertNull(owd);
	}

	public void testDirAnExistingPath() {
		String[] expected = OwClientTestData.rootPaths;
		String[] actual = owc.dir("/");
		assertEquals(expected, actual);
	}

	public void testDirANonExistingPath() {
		String[] expected = null;
		String[] actual = owc.dir("");
		assertEquals(expected, actual);
	}

	public void testReadAnAttachedDevice() {
		String expected = "20";
		String actual = owc
				.read("/1F.16E103000000/main/10.16438A010800/temperature");
		assertEquals(expected, actual);
	}

	public void testReadANotAttachedDevice() {
		String expected = null;
		String actual = owc
				.read("/1F.16E103000000/main/10.xxxxxxxxxxxx/temperature");
		assertEquals(expected, actual);
	}

	public void testReadNonExistingPath1() {
		String expected = null;
		String actual = owc
				.read("/1F.16E103000000/main/10.xxxxxxxxxxxx/temperature");
		assertEquals(expected, actual);
	}

	public void testReadNonExistingPath2() {
		String expected = null;
		String actual = owc.read("/1F.16E103000000/abcdefg");
		assertEquals(expected, actual);
	}

	public void testListAllAttachedDevices() {
		List<OwDevice> deviceList = owc.list();

		assertEquals(deviceList.size(), OwClientTestData.TOTALDEVICES);
	}

	public void testListNoAttachedDevices() throws Exception {
		OWNet tempOWNet = mock(OWNet.class);
		String[] str = null;
		when(tempOWNet.Dir("/")).thenReturn(str);
		when(tempOWNet.DirAll("/")).thenReturn(str);
		when(tempOWNet.DirAll("/1F.16E103000000")).thenReturn(str);
		when(tempOWNet.DirAll("/1F.16E103000000/")).thenReturn(str);
		when(tempOWNet.DirAll("/1F.16E103000000/main")).thenReturn(str);
		when(tempOWNet.DirAll("/1F.16E103000000/main/")).thenReturn(str);

		OwClient tempClient = new OwClient(tempOWNet);

		int expected = 0;
		int actual = tempClient.list().size();
		assertEquals(expected, actual);
	}

	public void testListSingleDeviceType() throws UnsupportedDeviceException {
		List<OwDevice> list = owc.list("10");
		int expected = 1;
		int actual = list.size();
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithSupportedFamCode1() {
		boolean expected = true;
		boolean actual = OwClient.isSupported("10");
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithSupportedFamCode2() {
		boolean expected = true;
		boolean actual = OwClient.isSupported("1F");
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithSupportedFamCode3() {
		boolean expected = true;
		boolean actual = OwClient.isSupported("26");
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithSupportedFamCode4() {
		boolean expected = true;
		boolean actual = OwClient.isSupported("81");
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithSupportedDeviceId() {
		boolean expected = true;
		boolean actual = OwClient.isSupported("10.16438A010800");
		assertEquals(expected, actual);
	}

	public void testIsSupportedWithNotSupportedDevice() {
		boolean expected = false;
		boolean actual = OwClient.isSupported("xx");
		assertEquals(expected, actual);
	}

	public void testIsSupportedSensorWithSupportedDeviceId() {
		boolean expected = true;
		boolean actual = OwClient.isSupportedSensor("10.16438A010800");
		assertEquals(expected, actual);
	}

	public void testIsSupportedSensorWithSupportedFamCode() {
		boolean expected = true;
		boolean actual = OwClient.isSupportedSensor("26");
		assertEquals(expected, actual);
	}

	public void testIsSupportedSensorWithNotSupportedDevice1() {
		boolean expected = false;
		boolean actual = OwClient.isSupportedSensor("1F");
		assertEquals(expected, actual);
	}

	public void testIsSupportedSensorWithNotSupportedDevice2() {
		boolean expected = false;
		boolean actual = OwClient.isSupportedSensor("xx");
		assertEquals(expected, actual);
	}
}
