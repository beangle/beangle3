/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.ldap.auth;

import java.security.NoSuchAlgorithmException;

import org.beangle.security.ldap.connect.LdapUserStore;

public class DefaultLdapValidator implements LdapValidator {

  private LdapUserStore userStore;

  public DefaultLdapValidator() {
    super();
  }

  public DefaultLdapValidator(LdapUserStore userStore) {
    super();
    this.userStore = userStore;
  }

  public boolean verifyPassword(String name, String password) {
    String ldapPwd = userStore.getPassword(name);
    try {
      return (null != ldapPwd) && (LdapPasswordHandler.getInstance().verify(ldapPwd, password));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public void setUserStore(LdapUserStore userStore) {
    this.userStore = userStore;
  }
}
