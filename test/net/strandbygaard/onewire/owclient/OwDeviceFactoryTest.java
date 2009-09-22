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

import junit.framework.TestCase;

public class OwDeviceFactoryTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCanCreate1() {
		boolean expected = true;
		boolean actual = OwDeviceFactory.canCreate("10");
		assertEquals(expected, actual);
	}

	public void testCanCreate2() {
		boolean expected = true;
		boolean actual = OwDeviceFactory.canCreate("26");
		assertEquals(expected, actual);
	}

	public void testCanCreate3() {
		boolean expected = true;
		boolean actual = OwDeviceFactory.canCreate("1F");
		assertEquals(expected, actual);
	}

	public void testCanCreate4() {
		boolean expected = true;
		boolean actual = OwDeviceFactory.canCreate("81");
		assertEquals(expected, actual);
	}
}
