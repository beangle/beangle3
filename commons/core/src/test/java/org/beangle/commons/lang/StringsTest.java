/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang;

import static org.beangle.commons.lang.Strings.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class StringsTest {

  public void testCount() {
    String targetStr = "11001101101111";
    String searchStr = "11";
    assertEquals(count(targetStr, searchStr), 5);
  }

  public void testUnCamel() {
    assertEquals(unCamel("MyCountInI_cbc", '-'), "my-count-ini_cbc");
    assertEquals(unCamel("MyCounT", '_'), "my_count");
    assertEquals(unCamel("MYCOUNT", '-'), "mycount");
    assertEquals(unCamel("parent_id", '_'), "parent_id");
    assertEquals(unCamel("parentId", '_'), "parent_id");
  }

  public void testSplit2() {
    String target = " abc ,; def ,;; ghi\r\n opq";
    String[] codes = split(target);
    assertEquals(codes.length, 4);
    assertEquals(codes[3], "opq");
  }

  public void testIsEqualSeq() {
    String first = "123,4546,";
    String second = ",4546,123";
    assertTrue(isEqualSeq(first, second));
    assertTrue(isEqualSeq(first, second, ","));
  }

  public void testMergeSeq() {
    String first = ",1,2,";
    String second = "3,";
    String third = "";
    String forth = null;
    assertTrue(isEqualSeq(mergeSeq(first, second), ",1,2,3,"));
    assertTrue(isEqualSeq(mergeSeq(first, second), ",1,2,3,"));
    assertTrue(isEqualSeq(mergeSeq(first, third), ",1,2,"));
    assertTrue(isEqualSeq(mergeSeq(first, forth), ",1,2,"));
  }

  public void testSplitNumSeq() throws Exception {
    String a = "1-2,3,5-9,3,";
    Integer[] nums = splitNumSeq(a);
    assertEquals(nums.length, 8);
  }

  public void testMisc() {
    assertEquals(",2,", subtractSeq("1,2", "1"));
    assertFalse(isEqualSeq(",2005-9,", ",2005-9,2006-9,"));
  }

  public void testRepeat() {
    assertEquals("", repeat("ad", 0));
    assertEquals("adadad", repeat("ad", 3));
  }

  public void testSplit() {
    assertEquals(new String[] { "a", "b", "c" }, split("a.b.c.", '.'));
    assertEquals(new String[] { "a", "b", "c" }, split(".a..b.c", '.'));
    assertEquals(new String[] { "a:b:c" }, split("a:b:c", '.'));
    assertEquals(new String[] {}, split("", (String)null));
    assertEquals(new String[] { "abc", "def" }, split("abc def", (String)null));
    assertEquals(new String[] { "abc", "def" }, split("abc def", " "));
    assertEquals(new String[] { "ab", "cd", "ef" }, split("ab:cd:ef", ":"));
  }

  public void testReplace() {
    assertEquals(replace(null, "x", null), null);
    assertEquals(replace("", "dd", "xx"), "");
    assertEquals(replace("any", null, "xx"), "any");
    assertEquals(replace("any", "d", null), "any");
    assertEquals(replace("any", "", "dd"), "any");
    assertEquals(replace("aba", "a", null), "aba");
    assertEquals(replace("aba", "a", ""), "b");
    assertEquals(replace("aba", "a", "z"), "zbz");

  }
}
