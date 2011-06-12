/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.system;

import org.beangle.ems.dictionary.service.impl.BaseCodeServiceImpl;
import org.beangle.ems.dictionary.service.impl.SeqCodeGenerator;
import org.beangle.ems.web.action.LogoutAction;
import org.beangle.emsapp.portal.action.HomeAction;
import org.beangle.emsapp.portal.action.LoginAction;
import org.beangle.emsapp.system.action.CodeMetaAction;
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
		// home
		bind(LoginAction.class, LogoutAction.class, HomeAction.class).in(
				Scope.PROTOTYPE);
		
		bind("baseCodeService", BaseCodeServiceImpl.class);
		bind(SeqCodeGenerator.class);

		bind(CodeMetaAction.class).in(Scope.PROTOTYPE);
	}

}
