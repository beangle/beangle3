/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.security.auth.dao.DaoAuthenticationProvider;
import org.beangle.security.auth.encoding.DigestPasswordEncoder;
import org.beangle.security.auth.encoding.PlaintextPasswordEncoder;
import org.beangle.security.core.session.category.DbSessionController;
import org.beangle.security.core.session.category.SessioninfoCleaner;
import org.beangle.security.core.session.impl.DbSessionRegistry;
import org.beangle.security.core.session.impl.MemSessionRegistry;

/**
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 17, 2011 7:56:25 PM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // session control bean
    bind(DbSessionRegistry.class, DigestPasswordEncoder.class).primary();
    bind(MemSessionRegistry.class, DbSessionController.class, SessioninfoCleaner.class).shortName();

    bind(DaoAuthenticationProvider.class).shortName();
    bind(PlaintextPasswordEncoder.class);
  }
}
