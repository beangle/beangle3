/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import org.beangle.spring.bind.AbstractBeanConfigBindFactory;
import org.beangle.spring.bind.Scope;
import org.beangle.spring.testbean.UserDaoProvider;
import org.beangle.spring.testbean.UserLdapProvider;

public class TestConfigFactory extends AbstractBeanConfigBindFactory {

	@Override
	protected void doBinding() {
		bind(SomeAction.class).in(Scope.PROTOTYPE);
		bind(UserLdapProvider.class, UserDaoProvider.class).shortName();
	}

}
