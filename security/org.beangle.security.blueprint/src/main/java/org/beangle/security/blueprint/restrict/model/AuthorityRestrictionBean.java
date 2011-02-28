/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import javax.persistence.Entity;

import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.restrict.AuthorityRestriction;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

@Entity(name = "org.beangle.security.blueprint.restrict.AuthorityRestriction")
public class AuthorityRestrictionBean extends RestrictionBean implements AuthorityRestriction {

	private static final long serialVersionUID = -4061917734606523063L;

	private Authority holder;

	public RestrictionHolder<? extends Restriction> getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder<? extends Restriction> holder) {
		this.holder = (Authority) holder;
	}

}
