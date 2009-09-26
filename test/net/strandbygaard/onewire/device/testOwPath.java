package net.strandbygaard.onewire.device;

import junit.framework.TestCase;

public class testOwPath extends TestCase {

	private OwPath path;

	protected void setUp() throws Exception {
		super.setUp();
		path = new OwPath("/1F.16E103000000/main/26.373BB6000000/");
	}

	public void testOwPath1() {
		path = new OwPath("/1F.16E103000000/main/26.373BB6000000/");
		final String expected = "/1F.16E103000000/main/26.373BB6000000";
		final String actual = path.getPath();
		assertEquals(expected, actual);
	}

	public void testOwPath2() {
		path = new OwPath("");
		final String expected = "";
		final String actual = path.getPath();
		assertEquals(expected, actual);
	}

	public void testOwPath3() {
		path = new OwPath(null);
		final String expected = "";
		final String actual = path.getPath();
		assertEquals(expected, actual);
	}

	public void testGetPath() {
		final String expected = "/1F.16E103000000/main/26.373BB6000000";
		final String actual = path.getPath();
		assertEquals(expected, actual);
	}

	public void testGetIdFromPath() {
		final String expected = "26.373BB6000000";
		final String actual = path.getIdFromPath().getId();
		assertEquals(expected, actual);
	}
}
