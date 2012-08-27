/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate;

import org.beangle.commons.context.inject.Resources;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.orm.DefaultTableNamingStrategy;
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
