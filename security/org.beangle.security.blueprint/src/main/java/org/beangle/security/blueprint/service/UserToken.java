/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.beangle.security.blueprint.Category;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.User;
import org.beangle.security.web.session.category.CategoryPrincipal;

public class UserToken extends User implements CategoryPrincipal, Comparable<UserToken> {

	private static final long serialVersionUID = 63829183922466239L;

	private final Long id;

	private final String fullname;

	/** 用户类别 */
	private Category category;

	public UserToken(Long id, String username, String fullname, String password, Category category,
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

	public Category getCategory() {
		return category;
	}

	public void changeCategory(Object newCategory) {
		Validate.isTrue(newCategory instanceof Category, "newCategory should be instanceof UserCategory");
		this.category = (Category) newCategory;
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
