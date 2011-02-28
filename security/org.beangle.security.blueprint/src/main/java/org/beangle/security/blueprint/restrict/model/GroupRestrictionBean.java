/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import javax.persistence.Entity;

import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.restrict.GroupRestriction;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

@Entity(name = "org.beangle.security.blueprint.restrict.GroupRestriction")
public class GroupRestrictionBean extends RestrictionBean implements GroupRestriction {

	private static final long serialVersionUID = -8655931585994557645L;

	private Group holder;

	public RestrictionHolder<? extends Restriction> getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder<? extends Restriction> holder) {
		this.holder = (Group) holder;
	}

}
