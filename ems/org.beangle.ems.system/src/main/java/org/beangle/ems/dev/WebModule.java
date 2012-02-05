/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.context.inject.Scope;
import org.beangle.ems.dev.hibernate.action.CacheAction;
import org.beangle.ems.dev.spring.action.SpringAction;
import org.beangle.ems.dev.struts2.action.ConfigBrowserAction;

public final class WebModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(SpringAction.class, ConfigBrowserAction.class, CacheAction.class).in(Scope.SINGLETON);
	}

}
