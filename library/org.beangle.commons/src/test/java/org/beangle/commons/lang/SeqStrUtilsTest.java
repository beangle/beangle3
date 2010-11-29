/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class SeqStrUtilsTest {
	@Test
	public void testIsEqualSeq() {
		String first = "123,4546,";
		String second = ",4546,123";
		assertTrue(SeqStrUtils.isEqualSeq(first, second));
		assertTrue(SeqStrUtils.isEqualSeq(first, second, ","));
	}

	@Test
	public void testMergeSeq() {
		String first = ",1,2,";
		String second = "3,";
		String third = "";
		String forth = null;
		assertTrue(SeqStrUtils.isEqualSeq(SeqStrUtils.mergeSeq(first, second), ",1,2,3,"));
		assertTrue(SeqStrUtils.isEqualSeq(SeqStrUtils.mergeSeq(first, second), ",1,2,3,"));
		assertTrue(SeqStrUtils.isEqualSeq(SeqStrUtils.mergeSeq(first, third), ",1,2,"));
		assertTrue(SeqStrUtils.isEqualSeq(SeqStrUtils.mergeSeq(first, forth), ",1,2,"));
	}

	@Test
	public void testSplitNumSeq() throws Exception {
		String a = "1-2,3,5-9,3,";
		Integer[] nums = SeqStrUtils.splitNumSeq(a);
		assertEquals(nums.length, 8);
	}

	@Test
	public void testMisc() {
		assertEquals(",2,", SeqStrUtils.subtractSeq("1,2", "1"));
		assertFalse(SeqStrUtils.isEqualSeq(",2005-9,", ",2005-9,2006-9,"));
	}
}
