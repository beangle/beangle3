/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.restrict.GroupRestriction;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "org.beangle.ems.security.restrict.GroupRestriction")
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupRestrictionBean extends RestrictionBean implements GroupRestriction {

	private static final long serialVersionUID = -8655931585994557645L;

	@NotNull
	private Group holder;

	public RestrictionHolder<? extends Restriction> getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder<? extends Restriction> holder) {
		this.holder = (Group) holder;
	}

}
