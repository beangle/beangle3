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
package org.beangle.commons.orm;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.spring.SpringResources;
import org.beangle.commons.entity.metadata.impl.ConvertPopulatorBean;
import org.beangle.commons.orm.hibernate.HibernateEntityDao;
import org.beangle.commons.orm.hibernate.HibernateTransactionManager;
import org.beangle.commons.orm.hibernate.RailsNamingStrategy;
import org.beangle.commons.orm.hibernate.SessionFactoryBean;
import org.beangle.commons.orm.hibernate.internal.HibernateEntityContext;
import org.beangle.commons.orm.hibernate.internal.HibernateModelMeta;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

public class DefaultModule extends AbstractBindModule {
  @Override
  protected void doBinding() {
    bind("dataSource", DriverManagerDataSource.class).property("driverClassName", "org.h2.Driver")
        .property("url", "jdbc:h2:./target/beangle;AUTO_SERVER=TRUE").property("username", "sa")
        .property("password", "");

    bind("hibernateConfig", PropertiesFactoryBean.class).property(
        "properties",
        props(
            // "hibernate.temp.use_jdbc_metadata_defaults=false",
            // "hibernate.dialect=org.hibernate.dialect.H2Dialect",
            // "hibernate.hbm2ddl.auto=validate",
            "hibernate.max_fetch_depth=1", "hibernate.default_batch_fetch_size=500",
            "hibernate.jdbc.fetch_size=8", "hibernate.jdbc.batch_size=20",
            "hibernate.jdbc.batch_versioned_data=true", "hibernate.jdbc.use_streams_for_binary=true",
            "hibernate.jdbc.use_get_generated_keys=true",
            "hibernate.cache.region.factory_class=org.hibernate.cache.EhCacheRegionFactory",
            "hibernate.cache.use_second_level_cache=true", "hibernate.cache.use_query_cache=true",
            "hibernate.query.substitutions=true 1, false 0, yes 'Y', no 'N'", "hibernate.show_sql=false"));

    bind("sessionFactory", SessionFactoryBean.class)
        .property("configurationClass", "org.beangle.commons.orm.hibernate.internal.OverrideConfiguration")
        .property("hibernateProperties", ref("hibernateConfig"))
        .property("configLocations", "classpath*:META-INF/hibernate.cfg.xml");

    bind(HibernateTransactionManager.class);

    bind("baseTransactionProxy", TransactionProxyFactoryBean.class).setAbstract().property(
        "transactionAttributes",
        props("save*=PROPAGATION_REQUIRED", "update*=PROPAGATION_REQUIRED", "delete*=PROPAGATION_REQUIRED",
            "batch*=PROPAGATION_REQUIRED", "execute*=PROPAGATION_REQUIRED", "remove*=PROPAGATION_REQUIRED",
            "create*=PROPAGATION_REQUIRED", "init*=PROPAGATION_REQUIRED", "authorize*=PROPAGATION_REQUIRED",
            "*=PROPAGATION_REQUIRED,readOnly"));

    bind(RailsNamingStrategy.class).shortName();
    bind(DefaultTableNamingStrategy.class).property(
        "resources",
        bean(SpringResources.class).property("locations", "classpath*:META-INF/beangle/table.properties")
            .property("users", "classpath*:beangle/table.properties"));

    bind(HibernateModelMeta.class, ConvertPopulatorBean.class, HibernateEntityContext.class);

    bind("entityDao", TransactionProxyFactoryBean.class).proxy("target", HibernateEntityDao.class).parent(
        "baseTransactionProxy");
    bind(DefaultLobHandler.class).shortName();
  }

}
