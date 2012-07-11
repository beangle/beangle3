/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.spring.SpringResources;
import org.beangle.commons.orm.hibernate.RailsNamingStrategy;
import org.beangle.commons.orm.hibernate.internal.HibernateModelBuilder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

public class DefaultModule extends AbstractBindModule {
  @Override
  protected void doBinding() {
    bind(RailsNamingStrategy.class).shortName();
    bind(DefaultTableNamingStrategy.class).property(
        "resources",
        bean(SpringResources.class)
            .property("locations", "classpath*:META-INF/beangle/table.properties").property("users",
                "classpath*:beangle/table.properties"));

    bind(HibernateModelBuilder.class);

    bind(DefaultLobHandler.class).shortName().lazy();
  }

}
