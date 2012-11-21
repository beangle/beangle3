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
package org.beangle.commons.context.property;

import static org.testng.Assert.assertEquals;

import java.util.Properties;

import org.beangle.commons.context.inject.Resources;
import org.beangle.commons.context.property.MultiProviderPropertyConfig;
import org.beangle.commons.context.property.PropertyConfig;
import org.beangle.commons.context.property.UrlPropertyConfigProvider;
import org.beangle.commons.lang.ClassLoaders;
import org.testng.annotations.Test;

@Test
public class UrlConfigProviderTest {

  public void testConfig() {
    PropertyConfig config = new MultiProviderPropertyConfig();
    UrlPropertyConfigProvider provider = new UrlPropertyConfigProvider();
    Resources resources = new Resources();
    // META-INF/system.properties
    resources.setGlobal(ClassLoaders.getResource("system-default.properties", getClass()));
    resources.setUser(ClassLoaders.getResource("system.properties", getClass()));
    provider.setResources(resources);
    Properties properties = provider.getConfig();
    config.add(properties);
    assertEquals(Integer.valueOf(1), config.get(Integer.class, "testInt"));
    assertEquals("beangle.org", config.get("system.vendor"));
    assertEquals("http://localhost", config.get("system.url"));
  }
}
