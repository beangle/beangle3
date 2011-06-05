/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.authority;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.GrantedAuthority;

/**
 * Basic concrete implementation of a {@link GrantedAuthority}.
 */

public class GrantedAuthorityBean implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;
	private String role;

	public GrantedAuthorityBean(String role) {
		Validate.notEmpty(role, "A granted authority textual representation is required");
		this.role = role;
	}

	public static List<GrantedAuthority> build(String... roles) {
		List<GrantedAuthority> authorities = CollectUtils.newArrayList(roles.length);
		for (String role : roles) {
			authorities.add(new GrantedAuthorityBean(role));
		}
		return authorities;
	}

	public boolean equals(Object obj) {
		// if (obj instanceof String) { return obj.equals(this.role); }
		if (obj instanceof GrantedAuthority) {
			GrantedAuthority attr = (GrantedAuthority) obj;
			return this.role.equals(attr.getAuthority());
		}
		return false;
	}

	public String getAuthority() {
		return this.role;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}

	public int compareTo(GrantedAuthority o) {
		if (o != null) {
			String rhsRole = ((GrantedAuthority) o).getAuthority();
			if (rhsRole == null) { return -1; }
			return role.compareTo(rhsRole);
		}
		return -1;
	}
}
