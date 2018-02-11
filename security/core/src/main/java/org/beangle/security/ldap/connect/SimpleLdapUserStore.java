/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.ldap.connect;

import java.util.Hashtable;
import java.util.Set;

import javax.naming.CompositeName;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.beangle.commons.bean.Disposable;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLdapUserStore implements LdapUserStore, Disposable {
  private Logger logger = LoggerFactory.getLogger(SimpleLdapUserStore.class);
  private String url;
  private String userName;
  private String password;
  private String base;
  private DirContext ctx;
  private String uidName = "uid";
  private Hashtable<String, String> properties = new Hashtable<String, String>();

  public SimpleLdapUserStore() {
    super();
  }

  public SimpleLdapUserStore(String url, String userName, String password, String base) {
    super();
    Assert.notNull(url);
    Assert.notNull(userName);
    Assert.notNull(password);
    Assert.notNull(base);
    this.url = url;
    this.userName = userName;
    this.password = password;
    this.base = base;
  }

  public String getUserDN(String uid) {
    DirContext ctx = getContext();
    if (ctx == null) return null;
    String result = null;
    String condition = Strings.concat(uidName, "=", uid);
    try {
      String attrList[] = { uidName };
      SearchControls constraints = new SearchControls();
      constraints.setSearchScope(2);
      constraints.setReturningAttributes(attrList);
      NamingEnumeration<SearchResult> results = ctx.search(base, condition, constraints);
      if (results.hasMore()) {
        SearchResult si = results.next();
        result = Strings.concat(si.getName(), ",", base);
      }
      results.close();
      results = null;
    } catch (Throwable e) {
      logger.error("Ldap search error,uid=" + uid, e);
    }
    return result;
  }

  public String getPassword(String uid) {
    Set<Attribute> passwords = getAttributes(uid, "userPassword");
    if (passwords.isEmpty()) return null;
    for (Attribute attr : passwords) {
      byte encPassword[];
      try {
        encPassword = (byte[]) attr.get();
      } catch (NamingException e) {
        logger.error("get password of " + uid + "error", e);
        return null;
      }
      return new String(encPassword);
    }
    return null;
  }

  public Set<Attribute> getAttributes(String uid, String attrName) {
    Set<Attribute> values = CollectUtils.newHashSet();
    DirContext ctx = getContext();
    if (ctx == null) return values;
    try {
      String dn = getUserDN(uid);
      if (dn == null) {
        logger.debug("User {} not found", uid);
        return values;
      }
      javax.naming.Name userID = new CompositeName(dn);
      Attributes attrs = null;
      if (null != attrName) {
        attrs = ctx.getAttributes(userID, new String[] { attrName });
      } else {
        attrs = ctx.getAttributes(userID);
      }
      for (NamingEnumeration<? extends Attribute> ne = attrs.getAll(); ne.hasMoreElements();) {
        Attribute attr = ne.nextElement();
        values.add(attr);
      }
    } catch (NamingException e) {
      e.printStackTrace();
    }
    return values;
  }

  private Hashtable<String, String> getBuildEnv() {
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    env.put("java.naming.provider.url", url);
    env.put("java.naming.security.authentication", "simple");
    env.put("java.naming.security.principal", userName);
    env.put("java.naming.security.credentials", password);
    return env;
  }

  private synchronized boolean connect() {
    Hashtable<String, String> env = this.getBuildEnv();
    env.putAll(properties);
    try {
      ctx = new InitialDirContext(env);
      logger.debug("Ldap server connect success.");
      return true;
    } catch (Exception e) {
      logger.error("Ldap server connect failure", e);
    }
    return false;
  }

  public synchronized void disConnect() {
    if (ctx != null) try {
      ctx.close();
      ctx = null;
      logger.debug("Ldap connect closed.");
    } catch (NamingException e) {
      logger.error("Failure to close ldap connection.", e);
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public Hashtable<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Hashtable<String, String> properties) {
    this.properties = properties;
  }

  private DirContext getContext() {
    if (null == ctx) {
      connect();
    }
    return ctx;
  }

  public void destroy() {
    this.disConnect();
  }

  public void setUidName(String uidName) {
    this.uidName = uidName;
  }

}
