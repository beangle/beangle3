/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.beangle.model.Entity;

public interface GenericDao<T extends Entity<ID>, ID extends Serializable> {

	public T get(ID id);

	public List<T> get(ID[] ids);

	public void saveOrUpdate(T entity);

	public void saveOrUpdate(Collection<T> entitis);

	public void remove(Collection<T> entitis);

	public void remove(T entity);

	public void remove(ID ids[]);

	public void remove(ID id);

	public Class<T> getEntityClass();

}
