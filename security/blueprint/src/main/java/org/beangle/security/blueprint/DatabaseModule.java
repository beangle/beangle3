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
package org.beangle.security.blueprint;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.orm.hibernate.HibernateEntityDao;
import org.beangle.orm.hibernate.HibernateTransactionManager;
import org.beangle.orm.hibernate.SessionFactoryBean;
import org.beangle.security.blueprint.session.service.DbSessionController;
import org.beangle.security.blueprint.session.service.DbSessionRegistry;
import org.beangle.security.blueprint.session.service.internal.DbSessioninfoLogServiceImpl;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

public class DatabaseModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("sessionDataSource", DriverManagerDataSource.class).property("driverClassName", "org.h2.Driver")
        .property("url", "jdbc:h2:./target/beangle;AUTO_SERVER=TRUE").property("username", "sa")
        .property("password", "");

    bind("sessionHibernateConfig", PropertiesFactoryBean.class).property(
        "properties",
        props("hibernate.max_fetch_depth=1", "hibernate.default_batch_fetch_size=500",
            "hibernate.jdbc.fetch_size=8", "hibernate.jdbc.batch_size=20",
            "hibernate.jdbc.batch_versioned_data=true", "hibernate.jdbc.use_streams_for_binary=true",
            "hibernate.jdbc.use_get_generated_keys=true",
            "hibernate.cache.region.factory_class=org.hibernate.cache.EhCacheRegionFactory",
            "hibernate.cache.use_second_level_cache=true", "hibernate.cache.use_query_cache=true",
            "hibernate.query.substitutions=true 1, false 0, yes 'Y', no 'N'", "hibernate.show_sql=false",
            "net.sf.ehcache.configurationResourceName=/ehcache-session.xml"));

    bind("sessionSessionFactory", SessionFactoryBean.class)
        .property("configurationClass", "org.beangle.orm.hibernate.internal.OverrideConfiguration")
        .property("hibernateProperties", ref("sessionHibernateConfig"))
        .property("persistLocations", "classpath*:META-INF/beangle/persist-session.properties")
        .property("dataSource", ref("sessionDataSource"));

    bind("sessionTransactionManager", HibernateTransactionManager.class).property("sessionFactory",
        ref("sessionSessionFactory"));

    bind("sessionEntityDao", TransactionProxyFactoryBean.class)
        .parent("baseTransactionProxy")
        .property("transactionManager", ref("sessionTransactionManager"))
        .proxy("target",
            bean(HibernateEntityDao.class).property("sessionFactory", ref("sessionSessionFactory")));

    bind(DbSessionRegistry.class, DbSessionController.class, DbSessioninfoLogServiceImpl.class)
        .property("entityDao", ref("sessionEntityDao")).shortName().primary();
  }

}
