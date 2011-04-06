/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.captcha;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.webapp.captcha.action.ImageAction;

public class CaptchaModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(ImageAction.class).in(Scope.PROTOTYPE);
	}

}
