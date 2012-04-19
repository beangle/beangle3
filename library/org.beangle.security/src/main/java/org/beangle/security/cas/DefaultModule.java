/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.security.cas.auth.CasAuthenticationProvider;
import org.beangle.security.cas.auth.vendor.NeusoftCasAliveChecker;
import org.beangle.security.cas.validation.Cas20ServiceTicketValidator;
import org.beangle.security.cas.web.CasEntryPoint;

/**
 * CAS Default Module
 * @author chaostone
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(CasConfig.class, CasEntryPoint.class, NeusoftCasAliveChecker.class,
				Cas20ServiceTicketValidator.class, CasAuthenticationProvider.class).shortName();
	}

}
