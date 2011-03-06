/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.blueprint.restrict.RestrictPattern;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.security.blueprint.restrict.UserRestriction;

@Entity(name = "org.beangle.security.blueprint.restrict.UserRestriction")
public class UserRestrictionBean extends RestrictionBean implements UserRestriction {
	private static final long serialVersionUID = 5582639967774356718L;

	@NotNull
	private User holder;

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
