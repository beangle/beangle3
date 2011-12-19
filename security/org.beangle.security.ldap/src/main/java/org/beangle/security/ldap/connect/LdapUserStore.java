/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.connect;

import java.util.Set;

import javax.naming.directory.Attribute;

/**
 * ldap user store
 * 
 * @author chaostone
 */
public interface LdapUserStore {

	String getUserDN(String uid);

	String getPassword(String userDn);

	Set<Attribute> getAttributes(String userDn, String attrName);
}
