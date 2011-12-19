/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SimpleLdapUserStore implements LdapUserStore, InitializingBean, DisposableBean {
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
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.base = base;
	}

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(url);
		Validate.notNull(userName);
		Validate.notNull(password);
		Validate.notNull(base);
	}

	public String getUserDN(String uid) {
		DirContext ctx = getContext();
		if (ctx == null) return null;
		String result = null;
		String condition = StrUtils.concat(uidName, "=", uid);
		try {
			String attrList[] = { uidName };
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(2);
			constraints.setReturningAttributes(attrList);
			NamingEnumeration<SearchResult> results = ctx.search(base, condition, constraints);
			if (results.hasMore()) {
				SearchResult si = results.next();
				result = StrUtils.concat(si.getName(), ",", base);
			}
			results.close();
			results = null;
		} catch (javax.naming.CommunicationException e1) {
			reset();
			logger.error("need reconnect to ldap");
		} catch (Throwable e) {
			logger.error("Ldap search error,uid=" + uid, e);
		}
		return result;
	}

	private void reset() {
		ctx = null;
	}

	public String getPassword(String userDN) {
		Set<Attribute> passwords = getAttributes(userDN, "userPassword");
		if (passwords.isEmpty()) return null;
		for (Attribute attr : passwords) {
			byte encPassword[];
			try {
				encPassword = (byte[]) attr.get();
			} catch (NamingException e) {
				logger.error("get password of " + userDN + "error", e);
				return null;
			}
			return new String(encPassword);
		}
		return null;
	}

	public Set<Attribute> getAttributes(String userDN, String attrName) {
		Set<Attribute> values = CollectUtils.newHashSet();
		DirContext ctx = getContext();
		if (ctx == null) return values;
		try {
			javax.naming.Name userID = new CompositeName(userDN);
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
		if (null != ctx) return true;
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

	public void destroy() throws Exception {
		this.disConnect();
	}

	public String getUidName() {
		return uidName;
	}

	public void setUidName(String uidName) {
		this.uidName = uidName;
	}

}
