/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

@Test
public class StrUtilsTest {

	public void testCountStr() {
		String targetStr = "11001101101111";
		String searchStr = "11";
		assertEquals(StrUtils.countStr(targetStr, searchStr), 5);
	}

	public void testUnCamel() {
		assertEquals(StrUtils.unCamel("MyCountInI_cbc", '-'), "my-count-ini_cbc");
		assertEquals(StrUtils.unCamel("MyCounT", '_'), "my_count");
		assertEquals(StrUtils.unCamel("MYCOUNT", '-'), "mycount");
		assertEquals(StrUtils.unCamel("parent_id", '_'), "parent_id");
		assertEquals(StrUtils.unCamel("parentId", '_'), "parent_id");
	}

	public void testSplit() {
		String target = " abc ,; def ,;; ghi\r\n opq";
		String[] codes = StrUtils.split(target);
		assertEquals(codes.length,4);
		assertEquals(codes[3],"opq");
	}
	
}
