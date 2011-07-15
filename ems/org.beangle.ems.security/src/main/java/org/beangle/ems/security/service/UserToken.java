/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.session.category.CategoryPrincipal;
import org.beangle.security.core.userdetail.User;

public class UserToken extends User implements CategoryPrincipal, Comparable<UserToken> {

	private static final long serialVersionUID = 63829183922466239L;

	private final Long id;

	private final String fullname;

	/** 用户类别 */
	private String category;

	public UserToken(Long id, String username, String fullname, String password, String category,
			boolean enabled, boolean accountExpired, boolean credentialsExpired, boolean accountLocked,
			Collection<? extends GrantedAuthority> authorities) throws IllegalArgumentException {
		super(username, password, enabled, accountExpired, credentialsExpired, accountLocked, authorities);
		this.id = id;
		this.fullname = fullname;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	public String getCategory() {
		return category;
	}

	public void changeCategory(String newCategory) {
		this.category =  newCategory;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getUsername().hashCode();
	}

	public int compareTo(UserToken o) {
		return this.fullname.compareTo(o.fullname);
	}

	/**
	 * 比较username,如果任一方username是null,则不相等
	 */
	public boolean equals(final Object object) {
		if (!(object instanceof UserToken)) { return false; }
		UserToken rhs = (UserToken) object;
		if (null == getUsername() || null == rhs.getUsername()) { return false; }
		return getUsername().equals(rhs.getUsername());
	}
}
