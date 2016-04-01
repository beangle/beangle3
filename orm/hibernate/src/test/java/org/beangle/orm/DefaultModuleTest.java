/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.orm;

import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.orm.hibernate.DefaultModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class DefaultModuleTest {
  private static final Logger logger = LoggerFactory.getLogger(DefaultModuleTest.class);

  public void testSpeed() {
    Stopwatch sw = new Stopwatch().start();
    for (int i = 0; i < 1; i++) {
      DefaultModule module = new DefaultModule();
      module.getConfig();
    }
    logger.debug(sw.toString());
  }
}
