/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.system;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.context.inject.Scope;
import org.beangle.ems.business.action.LogAction;
import org.beangle.ems.business.action.RuleAction;
import org.beangle.ems.business.action.RuleParamAction;
import org.beangle.ems.dictionary.action.CodeAction;
import org.beangle.ems.dictionary.action.CodeMetaAction;
import org.beangle.ems.dictionary.action.CodeScriptAction;
import org.beangle.ems.portal.action.HomeAction;
import org.beangle.ems.portal.action.LoginAction;
import org.beangle.ems.system.action.FileAction;
import org.beangle.ems.system.action.InfoAction;
import org.beangle.ems.system.action.PropertyAction;
import org.beangle.ems.web.action.LogoutAction;

public final class WebModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// property
		bind(FileAction.class, InfoAction.class, PropertyAction.class).in(Scope.PROTOTYPE);
		// home
		bind(LoginAction.class, LogoutAction.class, HomeAction.class).in(Scope.PROTOTYPE);

		bind(LogAction.class, RuleAction.class, RuleParamAction.class).in(Scope.PROTOTYPE);

		bind(CodeAction.class, CodeMetaAction.class, CodeScriptAction.class).in(Scope.PROTOTYPE);
	}

}
