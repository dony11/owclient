package net.strandbygaard.onewire.owclient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import junit.framework.TestCase;

import org.owfs.ownet.OWNet;

public class OwClientTest extends TestCase {

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

	final static int TOTALDEVICES = 3;
	private OWNet mockedOWNet;
	private OwClient owc;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		mockedOWNet = mock(OWNet.class);
		when(mockedOWNet.Dir("/")).thenReturn(rootPaths);
		when(mockedOWNet.DirAll("/")).thenReturn(rootPaths);

		when(mockedOWNet.DirAll("/1F.16E103000000")).thenReturn(portLocalPaths);
		when(mockedOWNet.DirAll("/1F.16E103000000/"))
				.thenReturn(portLocalPaths);

		when(mockedOWNet.DirAll("/1F.16E103000000/main")).thenReturn(
				portAttachedPaths);
		when(mockedOWNet.DirAll("/1F.16E103000000/main/")).thenReturn(
				portAttachedPaths);
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
		assertEquals(owc.dir("/"), rootPaths);
	}

	public void testDirANonExistingPath() {
		String[] str = null;
		assertEquals(owc.dir(""), str);
	}

	public void testReadAnAttachedDevice() {
		assertEquals(owc
				.read("/1F.16E103000000/main/10.16438A010800/temperature"),
				"20",
				owc.read("/1F.16E103000000/main/10.16438A010800/temperature"));
	}

	public void testReadANotAttachedDevice() {
		assertEquals(null, null, owc
				.read("/1F.16E103000000/main/10.xxxxxxxxxxxx/temperature"));
	}

	public void testReadNonExistingPath1() {
		assertEquals(null, null, owc
				.read("/1F.16E103000000/main/10.xxxxxxxxxxxx/temperature"));
	}

	public void testReadNonExistingPath2() {
		assertEquals(null, null, owc.read("/1F.16E103000000/abcdefg"));
	}

	public void testListAllAttachedDevices() {
		List<OwDevice> deviceList = owc.list();

		assertEquals(deviceList.size(), TOTALDEVICES);
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

		assertEquals(tempClient.list().size(), 0);
	}

	public void testListSingleDeviceType() throws UnsupportedDeviceException {
		List<OwDevice> list = owc.list("10");
		assertEquals(list.size(), 1);
	}

	public void testIsSupportedWithSupportedFamCode1() {
		assertEquals(true, OwClient.isSupported("10"));
	}

	public void testIsSupportedWithSupportedFamCode2() {
		assertEquals(true, OwClient.isSupported("1F"));
	}

	public void testIsSupportedWithSupportedFamCode3() {
		assertEquals(true, OwClient.isSupported("26"));
	}

	public void testIsSupportedWithSupportedFamCode4() {
		assertEquals(true, OwClient.isSupported("81"));
	}

	public void testIsSupportedWithSupportedDeviceId() {
		assertEquals(true, OwClient.isSupported("10.16438A010800"));
	}

	public void testIsSupportedWithNotSupportedDevice() {
		assertEquals(false, OwClient.isSupported("xx"));
	}

	public void testIsSupportedSensorWithSupportedDeviceId() {
		assertEquals(true, OwClient.isSupportedSensor("10.16438A010800"));
	}

	public void testIsSupportedSensorWithSupportedFamCode() {
		assertEquals(true, OwClient.isSupportedSensor("26"));
		assertEquals(true, OwClient.isSupportedSensor("81"));
	}

	public void testIsSupportedSensorWithNotSupportedDevice() {
		assertEquals(false, OwClient.isSupportedSensor("xx"));
		assertEquals(false, OwClient.isSupportedSensor("1F"));
	}
}
