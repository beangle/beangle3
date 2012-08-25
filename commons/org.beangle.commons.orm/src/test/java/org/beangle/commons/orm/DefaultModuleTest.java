/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm;

import org.beangle.commons.lang.time.Stopwatch;
import org.testng.annotations.Test;

@Test
public class DefaultModuleTest {

  public void testSpeed() {
    Stopwatch sw = new Stopwatch().start();
    for (int i = 0; i < 1; i++) {
      DefaultModule module = new DefaultModule();
      module.getConfig();
    }
    System.out.println(sw);
  }
}
