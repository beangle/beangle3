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
package org.beangle.commons.orm.example;

import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.testng.annotations.Test;

@Test
public class GenericTest {

  public void testInspect() throws Exception, NoSuchMethodException {
    Class<?> sc = Skill.class;
    Type gs = sc.getGenericSuperclass();
    System.out.println(gs);
    Method a = Skill.class.getMethod("getId");
    System.out.println(a.getReturnType());

    List<Skill> skills = CollectUtils.newArrayList();
    skills.add(new Skill());
    process(skills);
  }

  private void process(List<Skill> skills) {
    for (Skill s : skills) {
      assertNotNull(s);
    }
  }
}
