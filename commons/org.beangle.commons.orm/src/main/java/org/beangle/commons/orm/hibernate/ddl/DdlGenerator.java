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
package org.beangle.commons.orm.hibernate.ddl;

import java.io.IOException;

import org.beangle.commons.orm.DefaultTableNamingStrategy;
import org.beangle.commons.orm.hibernate.RailsNamingStrategy;
import org.beangle.commons.orm.hibernate.internal.OverrideConfiguration;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author chaostone
 * @version $Id: DdlGenerator.java Mar 22, 2012 9:45:57 AM chaostone $
 */
public class DdlGenerator {

  public void gen(String dialect, String fileName) throws HibernateException, IOException {
    Configuration configuration = new OverrideConfiguration();
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
        DdlGenerator.class.getClassLoader());

    configuration.getProperties().put(Environment.DIALECT, dialect);

    // config naming strategy
    DefaultTableNamingStrategy tableNamingStrategy = new DefaultTableNamingStrategy();
    for (Resource resource : resolver.getResources("classpath*:META-INF/beangle/table.properties"))
      tableNamingStrategy.addConfig(resource.getURL());
    RailsNamingStrategy namingStrategy = new RailsNamingStrategy();
    namingStrategy.setTableNamingStrategy(tableNamingStrategy);
    configuration.setNamingStrategy(namingStrategy);

    for (Resource resource : resolver.getResources("classpath*:META-INF/hibernate.cfg.xml"))
      configuration.configure(resource.getURL());
    SchemaExport export = new SchemaExport(configuration);
    export.setOutputFile(fileName);
    export.execute(false, false, false, true);
  }
}
