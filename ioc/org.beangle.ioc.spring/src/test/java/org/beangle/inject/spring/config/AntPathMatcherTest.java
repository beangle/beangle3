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
package org.beangle.inject.spring.config;

import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.commons.util.regex.AntPathPattern;
import org.springframework.util.AntPathMatcher;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AntPathMatcherTest {

  public void testSpring() {
    AntPathMatcher matcher = new AntPathMatcher();

    Assert.assertTrue(matcher.match("com/t?st.jsp", "com/test.jsp"));
    Assert.assertTrue(matcher.match("com/*.jsp", "com/test.jsp"));
    Assert.assertFalse(matcher.match("com/*.jsp", "com/dir/test.jsp"));

    Assert.assertTrue(matcher.match("com/**/test.jsp", "com/dir1/dir2/test.jsp"));
    Assert.assertTrue(matcher.match("com/beangle/**/*.jsp", "com/beangle/dir1/dir2/test3.jsp"));
    Assert.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/beangle/servlet/bla.jsp"));
    Assert.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/beangle/testing/servlet/bla.jsp"));
    Assert.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/servlet/bla.jsp"));

    Assert.assertFalse(matcher.match("org/**/servlet/bla.jsp", "org/anyservlet/bla.jsp"));
    Assert.assertTrue(matcher.match("org/**", "org/anyservlet/bla.jsp"));
  }

  public void testSpringPerf() {
    AntPathMatcher matcher = new AntPathMatcher();
    Stopwatch sw = new Stopwatch(true);
    int n = 10000;
    for (int i = 0; i < n; i++) {
      matcher.match("org/**/servlet/bla.jsp", "org/beangle/servlet/bla.jsp");
    }
    System.out.println("Spring AntPathMatcher " + n + "'s match using " + sw);
  }

  public void testBeanglePerf() {
    AntPathPattern pattern = new AntPathPattern("org/**/servlet/bla.jsp");
    Stopwatch sw = new Stopwatch(true);
    int n = 10000;
    for (int i = 0; i < n; i++) {
      pattern.match("org/beangle/servlet/bla.jsp");
    }
    System.out.println("Beangle AntPattern " + n + "'s match using " + sw);
  }

}
