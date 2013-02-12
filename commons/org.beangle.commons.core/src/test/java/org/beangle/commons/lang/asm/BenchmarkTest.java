/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;

import org.beangle.commons.lang.testbean.TestBean;
import org.beangle.commons.lang.time.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class BenchmarkTest {
  private static final Logger logger = LoggerFactory.getLogger(BenchmarkTest.class);
  int testCount = 1;// 100000000;

  public void testJdkReflect() throws Exception {
    logger.debug("testJdkReflect...");
    TestBean someObject = new TestBean();
    Method method = TestBean.class.getMethod("setName", String.class);
    for (int i = 0; i < 5; i++) {
      Stopwatch sw = new Stopwatch(true);
      for (int j = 0; j < testCount; j++) {
        method.invoke(someObject, "Unmi");
      }
      logger.debug(sw + " ");
    }
  }

  public void testReflectAsm() {
    logger.debug("testReflectAsm...");
    TestBean someObject = new TestBean();
    Mirror access = Mirror.get(TestBean.class);
    for (int i = 0; i < 5; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < testCount; j++) {
        access.invoke(someObject, access.getIndex("setName"), "Unmi");
      }
      logger.debug(System.currentTimeMillis() - begin + " ");
    }
  }
}
