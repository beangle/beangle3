/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.security.ldap.auth.DefaultLdapAuthenticationProvider;
import org.beangle.security.ldap.auth.DefaultLdapValidator;
import org.beangle.security.ldap.connect.SimpleLdapUserStore;

/**
 * Ldap default module
 * 
 * @author chaostone
 */
public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(SimpleLdapUserStore.class, DefaultLdapValidator.class, DefaultLdapAuthenticationProvider.class);
  }

}
