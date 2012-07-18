/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.testbean;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.inject.Scope;
import org.beangle.commons.context.spring.SpringResources;

public class TestModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(SomeAction.class).in(Scope.PROTOTYPE);
    bind(UserLdapProvider.class,UserDaoProvider.class).shortName();
    bind(TestService.class).shortName();

    bind(ProviderManager.class).property("providers", listref(UserDaoProvider.class));
    
    bind(SpringResourcesConsumer.class).property(
        "resources",
        bean(SpringResources.class)
            .property("locations", "classpath*:META-INF/beangle/table.properties").property("users",
                "classpath*:beangle/table.properties"));
  }

}
