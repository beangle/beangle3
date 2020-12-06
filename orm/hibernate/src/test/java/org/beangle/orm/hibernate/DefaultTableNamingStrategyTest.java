/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.orm.hibernate;

import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.security.model.AccessLog;
import org.beangle.security.model.TestUser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultTableNamingStrategyTest {

  @Test
  public void testGetSchemaName() {
    DefaultTableNamingStrategy config = new DefaultTableNamingStrategy();
    Resources resources = new Resources();
    //System.setProperty("beangle.data.orm.global_schema","test");
    resources.setGlobal(ClassLoaders.getResource("META-INF/beangle/table.properties", getClass()));
    config.setResources(resources);
    Assert.assertTrue(Strings.isEmpty(config.getSchema(TestUser.class.getName())));
    Assert.assertEquals("sys_", config.getPattern(TestUser.class).prefix);

    TableNamePattern p = config.getPattern(AccessLog.class);
    Assert.assertTrue(p != null);
    Assert.assertEquals(p.prefix, "l_");
    Assert.assertEquals(p.schema, "log");
  }

}
