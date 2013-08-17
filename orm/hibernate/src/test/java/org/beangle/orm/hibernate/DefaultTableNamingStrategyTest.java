/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.orm.hibernate;

import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.ClassLoaders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultTableNamingStrategyTest {

  @Test
  public void testGetSchemaName() {
    DefaultTableNamingStrategy config = new DefaultTableNamingStrategy();
    Resources resources = new Resources();
    resources.setGlobal(ClassLoaders.getResource("META-INF/beangle/table.properties", getClass()));
    config.setResources(resources);
    Assert.assertEquals("security_online", config.getSchema("org.beangle.security.online.model"));
    Assert.assertEquals("sys_", config.getPrefix("org.beangle.security"));
  }

}
