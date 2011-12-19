/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.auth;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

import org.beangle.security.ldap.connect.LdapUserStore;

/**
 * @author chaostone
 * @version $Id: BindValidator.java Sep 15, 2011 4:15:28 PM chaostone $
 */
public class SimpleBindValidator implements LdapValidator {

	private Hashtable<String, String> properties = new Hashtable<String, String>();

	private String url;

	private LdapUserStore userStore;

	public SimpleBindValidator() {
		super();
	}

	public SimpleBindValidator(String url) {
		super();
		this.url = url;
	}

	private Hashtable<String, String> getBuildEnv(String userName, String password) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
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
			throw new BadLdapCredentialsException();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
