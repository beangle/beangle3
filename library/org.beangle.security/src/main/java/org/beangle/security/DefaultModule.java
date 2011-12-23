/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.auth.dao.DaoAuthenticationProvider;
import org.beangle.security.auth.encoding.DigestPasswordEncoder;
import org.beangle.security.core.session.category.DbCategorySessionController;
import org.beangle.security.core.session.category.SessioninfoCleaner;
import org.beangle.security.core.session.impl.DbAccessLogger;
import org.beangle.security.core.session.impl.DbSessionRegistry;
import org.beangle.security.core.session.impl.MemSessionRegistry;
import org.beangle.spring.config.AbstractBindModule;

/**
 * @author chaostone
 * @version $Id: Module.java Jun 17, 2011 7:56:25 PM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// session control bean
		bind(MemSessionRegistry.class, DbSessionRegistry.class, DbCategorySessionController.class,
				SessioninfoCleaner.class).shortName();

		bind(DaoAuthenticationProvider.class).shortName();
		bind(DigestPasswordEncoder.class);
		bind(DbAccessLogger.class);
	}
}
