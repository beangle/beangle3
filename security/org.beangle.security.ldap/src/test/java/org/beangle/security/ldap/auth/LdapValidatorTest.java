package org.beangle.security.ldap.auth;

import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

import junit.framework.Assert;

import org.beangle.security.ldap.connect.LdapUserStore;
import org.beangle.security.ldap.connect.SimpleLdapUserStore;
import org.testng.annotations.Test;

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */

@Test
public class LdapValidatorTest {

	private SimpleLdapUserStore getStore(String url, String username, String password, String base) {
		return new SimpleLdapUserStore(url, username, password, base);
	}

	private void tryGet(LdapUserStore store, String name) {
		String dn = store.getUserDN(name);
		Assert.assertNotNull(dn);
		String pwd = store.getPassword(name);
		Assert.assertNotNull(pwd);
		Set<Attribute> attrs = store.getAttributes(name, null);
		Assert.assertNotNull(attrs);
	}

	private void tryTestPassword(LdapUserStore store, String name, String password) {
		LdapValidator ldapValidator = new DefaultLdapValidator(store);
		Assert.assertTrue(ldapValidator.verifyPassword(name, password));
	}

	public void testLdap() throws NamingException {
		
	}
}
