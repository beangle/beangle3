/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.context.inject.Scope;
import org.beangle.ems.avatar.action.BoardAction;
import org.beangle.ems.avatar.action.MyUploadAction;
import org.beangle.ems.security.action.AuthorityAction;
import org.beangle.ems.security.action.CaptchaAction;
import org.beangle.ems.security.action.GroupAction;
import org.beangle.ems.security.action.IndexAction;
import org.beangle.ems.security.action.MenuAction;
import org.beangle.ems.security.action.MenuNavAction;
import org.beangle.ems.security.action.MenuProfileAction;
import org.beangle.ems.security.action.MonitorAction;
import org.beangle.ems.security.action.MyAction;
import org.beangle.ems.security.action.PasswordAction;
import org.beangle.ems.security.action.ResourceAction;
import org.beangle.ems.security.action.RestrictMetaAction;
import org.beangle.ems.security.action.RestrictionAction;
import org.beangle.ems.security.action.SessioninfoLogAction;
import org.beangle.ems.security.action.UserAction;
import org.beangle.ems.security.helper.UserDashboardHelper;

public final class WebModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// security
		bind(SessioninfoLogAction.class, AuthorityAction.class, GroupAction.class, IndexAction.class,
				MenuAction.class, MenuNavAction.class, MenuProfileAction.class, MonitorAction.class,
				MyAction.class, PasswordAction.class, ResourceAction.class, RestrictionAction.class,
				RestrictMetaAction.class, UserAction.class, CaptchaAction.class).in(Scope.PROTOTYPE);

		bind(UserDashboardHelper.class).shortName();

		// avatar
		bind(BoardAction.class, org.beangle.ems.avatar.action.MyAction.class, MyUploadAction.class,
				org.beangle.ems.avatar.action.UserAction.class).in(Scope.PROTOTYPE);

	}

}
