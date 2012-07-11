/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class StringsTest {

  public void testCount() {
    String targetStr = "11001101101111";
    String searchStr = "11";
    assertEquals(Strings.count(targetStr, searchStr), 5);
  }

  public void testUnCamel() {
    assertEquals(Strings.unCamel("MyCountInI_cbc", '-'), "my-count-ini_cbc");
    assertEquals(Strings.unCamel("MyCounT", '_'), "my_count");
    assertEquals(Strings.unCamel("MYCOUNT", '-'), "mycount");
    assertEquals(Strings.unCamel("parent_id", '_'), "parent_id");
    assertEquals(Strings.unCamel("parentId", '_'), "parent_id");
  }

  public void testSplit() {
    String target = " abc ,; def ,;; ghi\r\n opq";
    String[] codes = Strings.split(target);
    assertEquals(codes.length, 4);
    assertEquals(codes[3], "opq");
  }

  public void testIsEqualSeq() {
    String first = "123,4546,";
    String second = ",4546,123";
    assertTrue(Strings.isEqualSeq(first, second));
    assertTrue(Strings.isEqualSeq(first, second, ","));
  }

  public void testMergeSeq() {
    String first = ",1,2,";
    String second = "3,";
    String third = "";
    String forth = null;
    assertTrue(Strings.isEqualSeq(Strings.mergeSeq(first, second), ",1,2,3,"));
    assertTrue(Strings.isEqualSeq(Strings.mergeSeq(first, second), ",1,2,3,"));
    assertTrue(Strings.isEqualSeq(Strings.mergeSeq(first, third), ",1,2,"));
    assertTrue(Strings.isEqualSeq(Strings.mergeSeq(first, forth), ",1,2,"));
  }

  public void testSplitNumSeq() throws Exception {
    String a = "1-2,3,5-9,3,";
    Integer[] nums = Strings.splitNumSeq(a);
    assertEquals(nums.length, 8);
  }

  public void testMisc() {
    assertEquals(",2,", Strings.subtractSeq("1,2", "1"));
    assertFalse(Strings.isEqualSeq(",2005-9,", ",2005-9,2006-9,"));
  }
}
