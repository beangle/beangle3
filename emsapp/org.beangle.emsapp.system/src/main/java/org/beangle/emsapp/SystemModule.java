/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp;

import org.beangle.emsapp.avatar.action.BoardAction;
import org.beangle.emsapp.avatar.action.MyUploadAction;
import org.beangle.emsapp.captcha.action.ImageAction;
import org.beangle.emsapp.portal.action.HomeAction;
import org.beangle.emsapp.portal.action.LoginAction;
import org.beangle.emsapp.portal.action.LogoutAction;
import org.beangle.emsapp.security.action.ActivityAction;
import org.beangle.emsapp.security.action.AuthorityAction;
import org.beangle.emsapp.security.action.GroupAction;
import org.beangle.emsapp.security.action.IndexAction;
import org.beangle.emsapp.security.action.MenuAction;
import org.beangle.emsapp.security.action.MenuProfileAction;
import org.beangle.emsapp.security.action.MonitorAction;
import org.beangle.emsapp.security.action.MyAction;
import org.beangle.emsapp.security.action.PasswordAction;
import org.beangle.emsapp.security.action.ResourceAction;
import org.beangle.emsapp.security.action.RestrictMetaAction;
import org.beangle.emsapp.security.action.RestrictionAction;
import org.beangle.emsapp.security.action.UserAction;
import org.beangle.emsapp.security.helper.UserDashboardHelper;
import org.beangle.emsapp.system.action.FileAction;
import org.beangle.emsapp.system.action.InfoAction;
import org.beangle.emsapp.system.action.PropertyAction;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

public final class SystemModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// property
		bind(FileAction.class, InfoAction.class, PropertyAction.class).in(
				Scope.PROTOTYPE);

		// security
		bind(ActivityAction.class, AuthorityAction.class, GroupAction.class,
				IndexAction.class, MenuAction.class, MenuProfileAction.class,
				MonitorAction.class, MyAction.class, PasswordAction.class,
				ResourceAction.class, RestrictionAction.class,
				RestrictMetaAction.class, UserAction.class).in(Scope.PROTOTYPE);
		bind(UserDashboardHelper.class).shortName();

		// captcha
		bind(ImageAction.class).in(Scope.PROTOTYPE);

		// avatar
		bind(BoardAction.class,
				org.beangle.emsapp.avatar.action.MyAction.class,
				MyUploadAction.class,
				org.beangle.emsapp.avatar.action.UserAction.class).in(
				Scope.PROTOTYPE);

		// home
		bind(LoginAction.class, LogoutAction.class, HomeAction.class).in(
				Scope.PROTOTYPE);
	}

}
