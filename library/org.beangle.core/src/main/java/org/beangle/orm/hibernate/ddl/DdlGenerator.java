/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate.ddl;

import java.io.IOException;

import org.beangle.orm.DefaultTableNamingStrategy;
import org.beangle.orm.hibernate.RailsNamingStrategy;
import org.beangle.orm.hibernate.internal.OverrideConfiguration;
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

	public void gen(String dialect,String fileName) throws HibernateException, IOException {
		Configuration configuration = new OverrideConfiguration();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
				DdlGenerator.class.getClassLoader());

		configuration.getProperties().put(Environment.DIALECT, dialect);
		
		//config naming strategy
		DefaultTableNamingStrategy tableNamingStrategy = new DefaultTableNamingStrategy();
		for (Resource resource : resolver.getResources("classpath*:META-INF/beangle/table.properties"))
			tableNamingStrategy.addConfig(resource.getURL());
		RailsNamingStrategy namingStrategy =new RailsNamingStrategy();
		namingStrategy.setTableNamingStrategy(tableNamingStrategy);
		configuration.setNamingStrategy(namingStrategy);
		
		for (Resource resource : resolver.getResources("classpath*:META-INF/hibernate.cfg.xml"))
			configuration.configure(resource.getURL());
		SchemaExport export = new SchemaExport(configuration);
		export.setOutputFile(fileName);
		export.execute(false, false, false, true);
	}
}
