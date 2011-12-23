/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security;

import org.beangle.emsapp.avatar.action.BoardAction;
import org.beangle.emsapp.avatar.action.MyUploadAction;
import org.beangle.emsapp.security.action.AuthorityAction;
import org.beangle.emsapp.security.action.CaptchaAction;
import org.beangle.emsapp.security.action.GroupAction;
import org.beangle.emsapp.security.action.IndexAction;
import org.beangle.emsapp.security.action.MenuAction;
import org.beangle.emsapp.security.action.MenuNavAction;
import org.beangle.emsapp.security.action.MenuProfileAction;
import org.beangle.emsapp.security.action.MonitorAction;
import org.beangle.emsapp.security.action.MyAction;
import org.beangle.emsapp.security.action.PasswordAction;
import org.beangle.emsapp.security.action.ResourceAction;
import org.beangle.emsapp.security.action.RestrictMetaAction;
import org.beangle.emsapp.security.action.RestrictionAction;
import org.beangle.emsapp.security.action.SessioninfoLogAction;
import org.beangle.emsapp.security.action.UserAction;
import org.beangle.emsapp.security.helper.UserDashboardHelper;
import org.beangle.spring.config.AbstractBindModule;
import org.beangle.spring.config.Scope;

public final class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// security
		bind(SessioninfoLogAction.class, AuthorityAction.class, GroupAction.class, IndexAction.class,
				MenuAction.class, MenuNavAction.class, MenuProfileAction.class, MonitorAction.class,
				MyAction.class, PasswordAction.class, ResourceAction.class, RestrictionAction.class,
				RestrictMetaAction.class, UserAction.class, CaptchaAction.class).in(Scope.PROTOTYPE);

		bind(UserDashboardHelper.class).shortName();

		// avatar
		bind(BoardAction.class, org.beangle.emsapp.avatar.action.MyAction.class, MyUploadAction.class,
				org.beangle.emsapp.avatar.action.UserAction.class).in(Scope.PROTOTYPE);

	}

}
