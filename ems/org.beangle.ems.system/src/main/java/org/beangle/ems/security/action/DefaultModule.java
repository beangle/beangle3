/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.context.inject.Scope;
import org.beangle.ems.avatar.action.BoardAction;
import org.beangle.ems.avatar.action.MyUploadAction;
import org.beangle.ems.security.helper.UserDashboardHelper;

public final class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// security
		bind(SessioninfoLogAction.class, AuthorityAction.class, RoleAction.class, IndexAction.class,
				MenuAction.class, MenuNavAction.class, MenuProfileAction.class, MonitorAction.class,
				MyAction.class, PasswordAction.class, ResourceAction.class, UserAction.class,
				CaptchaAction.class, ProfileAction.class, RestrictionAction.class).in(Scope.PROTOTYPE);

		bind(UserDashboardHelper.class).shortName();

		// avatar
		bind(BoardAction.class, org.beangle.ems.avatar.action.MyAction.class, MyUploadAction.class,
				org.beangle.ems.avatar.action.UserAction.class).in(Scope.PROTOTYPE);

	}

}
