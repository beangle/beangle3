/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

@Test
public class DefaultModuleTest {

  public void testSpeed() {
    StopWatch sw = new StopWatch();
    sw.start();
    for (int i = 0; i < 1; i++) {
      DefaultModule module = new DefaultModule();
      module.getConfig();
    }
    System.out.println(sw.getTime());
  }
}
