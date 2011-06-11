/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.User;
import org.beangle.ems.security.model.UserBean;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.restrict.UserRestriction;

@Entity(name = "org.beangle.ems.security.restrict.UserRestriction")
public class UserRestrictionBean extends RestrictionBean implements UserRestriction {
	private static final long serialVersionUID = 5582639967774356718L;

	@NotNull
	private User holder;

	public UserRestrictionBean() {
		super();
	}

	public UserRestrictionBean(User holder, RestrictPattern pattern) {
		super();
		this.holder = holder;
		this.pattern = pattern;
	}

	public RestrictionHolder<? extends Restriction> getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder<? extends Restriction> holder) {
		this.holder = (UserBean) holder;
	}

}
