/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.auth;

import java.security.NoSuchAlgorithmException;

import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.auth.dao.AbstractUserDetailAuthenticationProvider;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.User;
import org.beangle.security.core.userdetail.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.EntryNotFoundException;
import org.springframework.ldap.LdapTemplate;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DistinguishedName;

/**
 * 读取ldap的用户信息<br>
 * 
 * @author chaostone
 */
public class DefaultLdapAuthenticationProvider extends AbstractUserDetailAuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(DefaultLdapAuthenticationProvider.class);

	protected LdapTemplate ldapTemplate;

	protected String nameAttrName = "uid";

	protected String passwordAttrName = "userPassword";

	@Override
	protected void additionalAuthenticationChecks(UserDetail user,
			UsernamePasswordAuthentication authentication) throws AuthenticationException {
		try {
			LdapPasswordHandler.getInstance().verify(user.getPassword(),
					(String) authentication.getCredentials());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected UserDetail retrieveUser(String username, UsernamePasswordAuthentication authentication)
			throws AuthenticationException {
		DistinguishedName dn = new DistinguishedName(nameAttrName + "=" + username);
		String ldapPassword = null;
		try {
			ldapPassword = (String) ldapTemplate.lookup(dn, new String[] { "userPassword" },
					new ContextMapper() {
						public Object mapFromContext(Object ctx) {
							DirContextAdapter context = (DirContextAdapter) ctx;
							return new String((byte[]) context.getObjectAttribute("userPassword"));
						}
					});
		} catch (EntryNotFoundException e) {
			logger.info("cannot found {} in ldap", username);
			throw new AuthenticationException("ldap user " + username + " not found");
		}
		// FIXME user authorities
		return new User(username, ldapPassword, null);
	}

	public String getNameAttrName() {
		return nameAttrName;
	}

	public void setNameAttrName(String nameAttrName) {
		this.nameAttrName = nameAttrName;
	}

	public String getPasswordAttrName() {
		return passwordAttrName;
	}

	public void setPasswordAttrName(String passwordAttrName) {
		this.passwordAttrName = passwordAttrName;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

}
