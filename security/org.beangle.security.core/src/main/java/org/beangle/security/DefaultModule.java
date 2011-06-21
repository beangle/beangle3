/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.auth.dao.DaoAuthenticationProvider;
import org.beangle.security.auth.encoding.DigestPasswordEncoder;
import org.beangle.security.core.session.MemSessionRegistry;
import org.beangle.security.core.session.category.MultiCategorySessionController;
import org.beangle.spring.bind.AbstractBindModule;

/**
 * @author chaostone
 * @version $Id: Module.java Jun 17, 2011 7:56:25 PM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// session control bean
		bind(MultiCategorySessionController.class).shortName();

		bind(MemSessionRegistry.class, DaoAuthenticationProvider.class).shortName();
		bind(DigestPasswordEncoder.class);
	}

}
