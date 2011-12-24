/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev;

import org.beangle.ems.dev.action.HibernateAction;
import org.beangle.ems.dev.action.SpringAction;
import org.beangle.ems.dev.action.Struts2Action;
import org.beangle.spring.config.AbstractBindModule;
import org.beangle.spring.config.Scope;

public final class WebModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(SpringAction.class, Struts2Action.class, HibernateAction.class).in(Scope.PROTOTYPE);
	}

}
