/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.webapp.security.action.ActivityAction;
import org.beangle.webapp.security.action.AuthorityAction;
import org.beangle.webapp.security.action.GroupAction;
import org.beangle.webapp.security.action.IndexAction;
import org.beangle.webapp.security.action.MenuAction;
import org.beangle.webapp.security.action.MenuProfileAction;
import org.beangle.webapp.security.action.MonitorAction;
import org.beangle.webapp.security.action.MyAction;
import org.beangle.webapp.security.action.PasswordAction;
import org.beangle.webapp.security.action.ResourceAction;
import org.beangle.webapp.security.action.RestrictMetaAction;
import org.beangle.webapp.security.action.RestrictionAction;
import org.beangle.webapp.security.action.UserAction;
import org.beangle.webapp.security.helper.UserDashboardHelper;

public final class SecurityModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(ActivityAction.class, AuthorityAction.class, GroupAction.class, IndexAction.class,
				MenuAction.class, MenuProfileAction.class, MonitorAction.class, MyAction.class,
				PasswordAction.class, ResourceAction.class, RestrictionAction.class,
				RestrictMetaAction.class, UserAction.class).in(Scope.PROTOTYPE);
		bind(UserDashboardHelper.class).shortName();
	}
}
