/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author zhouqi
 */
public class PageAdapterTest {
  @Test
  public void testD() throws Exception {
    List<String> datas = new ArrayList<String>(26);
    for (int i = 0; i < 26; i++) {
      datas.add(String.valueOf(i));
    }
    Page<String> page = new PagedList<String>(datas, 20);
    assertNotNull(page.iterator());
    assertEquals(page.iterator().next(), "0");
    page.next();
    assertEquals(page.iterator().next(), "20");
    page.moveTo(2);
    assertEquals(page.iterator().next(), "20");
    page.previous();
    assertEquals(page.iterator().next(), "0");
  }
}
