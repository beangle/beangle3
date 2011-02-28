/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import junit.framework.Assert;

import org.testng.annotations.Test;

@Test
public class DbversionTest {

	public void testRange() {
		Dbversion version = new Dbversion("[1.0,2.0]");
		Assert.assertTrue(version.contains("1.0"));
		Assert.assertTrue(version.contains("1.0.1"));
		Assert.assertTrue(version.contains("1.5.2"));
		Assert.assertTrue(version.contains("2.0"));
		Assert.assertFalse(version.contains("0.9.beta"));
		Assert.assertFalse(version.contains("2.0.1"));
	}

	public void testSingle() {
		Dbversion version = new Dbversion("2.3.5");
		Assert.assertFalse(version.contains("2.3"));
		Assert.assertFalse(version.contains("2.4"));
		Assert.assertTrue(version.contains("2.3.5"));
	}

	public void testOpenRange1() {
		Dbversion version = new Dbversion("(2.3.5,)");
		Assert.assertFalse(version.contains("2.3"));
		Assert.assertTrue(version.contains("2.4"));
		Assert.assertTrue(version.contains("2.5"));
	}

	public void testOpenRange2() {
		Dbversion version = new Dbversion( "(,2.3.5]");
		Assert.assertTrue(version.contains("2.3.5"));
		Assert.assertTrue(version.contains("2.3"));
		Assert.assertFalse(version.contains("2.5"));
	}
}
