/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import java.util.Set;

import org.beangle.model.Entity;

public interface HierarchyEntity<T extends HierarchyEntity<?>> extends Entity<Long> {

	T getParent();

	public Set<? extends T> getChildren();
}
