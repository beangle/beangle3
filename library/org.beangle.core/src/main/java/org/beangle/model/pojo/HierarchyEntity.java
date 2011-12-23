/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import java.io.Serializable;
import java.util.List;

import org.beangle.model.Entity;

public interface HierarchyEntity<T,ID extends Serializable> extends Entity<ID> {

	public T getParent();

	public void setParent(T parent);

	public List<T> getChildren();

	public void setChildren(List<T> children);
}
