package net.strandbygaard.onewire.device;

import junit.framework.TestCase;

public class testOwId extends TestCase {

	private OwId owid;
	
	protected void setUp() throws Exception {
		super.setUp();
		owid = new OwId("10.16438A010800");
	}

	public void testOwId1() {
		final String expected = "10.16438A010800";
		owid = new OwId(expected);
		final String actual = owid.getId();
		assertEquals(expected, actual);
	}

	public void testOwId2() {
		final String expected = "";
		owid = new OwId("");
		final String actual = owid.getId();
		assertEquals(expected, actual);
	}

	public void testOwId3() {
		final String expected = "";
		owid = new OwId(null);
		final String actual = owid.getId();
		assertEquals(expected, actual);
	}

	public void testGetId() {
		final String expected = "10.16438A010800";
		final String actual = owid.getId();
		assertEquals(expected, actual);
	}

	public void testGetFamilyCode() {
		final String expected = "10";
		final String actual = owid.getFamilyCode();
		assertEquals(expected, actual);
	}
}
