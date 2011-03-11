/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.portal;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.webapp.portal.action.HomeAction;
import org.beangle.webapp.portal.action.LoginAction;
import org.beangle.webapp.portal.action.LogoutAction;

public class PortalModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(LoginAction.class, LogoutAction.class, HomeAction.class).in(Scope.PROTOTYPE);
	}

}
