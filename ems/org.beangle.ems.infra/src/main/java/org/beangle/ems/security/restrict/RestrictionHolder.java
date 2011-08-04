/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;

public interface RestrictionHolder<T extends Restriction> extends LongIdEntity {

	public Set<T> getRestrictions();

	public void setRestrictions(Set<T> restrictions);

}
