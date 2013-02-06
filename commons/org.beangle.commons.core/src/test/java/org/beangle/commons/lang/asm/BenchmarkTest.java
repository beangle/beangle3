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

public class BenchmarkTest {
  public static void main(String[] args) throws Exception {
    long begin = System.currentTimeMillis();
    TestBean[] data = new TestBean[256];
    int hashcode = 1984801293;
    for (int i = 0; i < 100000000; i++) {
      getData(data, hashcode++, 255);
    }
    System.out.println(System.currentTimeMillis() - begin + " ");
    testJdkReflect();
    testReflectAsm();
  }

  static TestBean getData(TestBean[] data, int hash, int mask) {
    return data[hash & mask];
  }

  public static void testJdkReflect() throws Exception {
    System.out.print("testJdkReflect...");
    TestBean someObject = new TestBean();
    Method method = TestBean.class.getMethod("setName", String.class);
    for (int i = 0; i < 5; i++) {
      Stopwatch sw = new Stopwatch(true);
      for (int j = 0; j < 100000000; j++) {
        method.invoke(someObject, "Unmi");
      }
      System.out.print(sw + " ");
    }
    System.out.println();
  }

  public static void testReflectAsm() {
    System.out.print("testReflectAsm...");
    TestBean someObject = new TestBean();
    AccessProxy access = AccessProxy.get(TestBean.class);
    for (int i = 0; i < 5; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < 100000000; j++) {
        access.invoke(someObject, access.getIndex("setName"), "Unmi");
      }
      System.out.print(System.currentTimeMillis() - begin + " ");
    }
  }
}
