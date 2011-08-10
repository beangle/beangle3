/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.Authority;
import org.beangle.ems.security.restrict.AuthorityRestriction;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;

@Entity(name = "org.beangle.ems.security.restrict.AuthorityRestriction")
public class AuthorityRestrictionBean extends RestrictionBean implements AuthorityRestriction {

	private static final long serialVersionUID = -4061917734606523063L;

	@NotNull
	private Authority holder;

	public RestrictionHolder<? extends Restriction> getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder<? extends Restriction> holder) {
		this.holder = (Authority) holder;
	}

}
