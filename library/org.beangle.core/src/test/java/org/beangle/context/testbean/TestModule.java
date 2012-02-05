/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.testbean;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.context.inject.Scope;

public class TestModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(SomeAction.class).in(Scope.PROTOTYPE);
		bind(UserLdapProvider.class, UserDaoProvider.class).shortName();
		bind(TestService.class).shortName();
	}

}
