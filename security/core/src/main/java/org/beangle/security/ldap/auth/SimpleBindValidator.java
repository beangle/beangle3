/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.ldap.connect.LdapUserStore;

/**
 * @author chaostone
 * @version $Id: BindValidator.java Sep 15, 2011 4:15:28 PM chaostone $
 */
public class SimpleBindValidator implements LdapValidator {

  private Hashtable<String, String> properties = new Hashtable<String, String>();

  private LdapUserStore userStore;

  public SimpleBindValidator() {
    super();
  }

  private Hashtable<String, String> getBuildEnv(String userName, String password) {
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, userStore.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, userName);
    env.put(Context.SECURITY_CREDENTIALS, password);
    return env;
  }

  public boolean verifyPassword(String userDN, String password) {
    Hashtable<String, String> env = getBuildEnv(userDN, password);
    env.putAll(properties);
    try {
      new InitialDirContext(env).close();
      return true;
    } catch (javax.naming.AuthenticationException ae) {
      throw new BadCredentialsException();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Hashtable<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Hashtable<String, String> properties) {
    this.properties = properties;
  }

  public LdapUserStore getUserStore() {
    return userStore;
  }

  public void setUserStore(LdapUserStore userStore) {
    this.userStore = userStore;
  }

  public String getUserDN(String name) {
    return userStore.getUserDN(name);
  }
}
