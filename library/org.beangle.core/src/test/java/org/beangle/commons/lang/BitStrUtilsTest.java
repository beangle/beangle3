/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class BitStrUtilsTest {

	@Test
	public void binValueOf() {
		assertEquals(BitStrUtils.binValueOf("00000000000000000000000000000000011111111111111111111"), 1048575);

		assertEquals(BitStrUtils.binValueOf("00000000000000000000000000000000000011100000000000000"), 114688);
	}
}
