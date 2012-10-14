/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.beangle.commons.entity.Entity;

/**
 * <p>
 * Dao interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Dao<T extends Entity<ID>, ID extends Serializable> {

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param id a ID object.
   * @return a T object.
   */
  public T get(ID id);

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param ids an array of ID objects.
   * @return a {@link java.util.List} object.
   */
  public List<T> get(ID[] ids);

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entity a T object.
   */
  public void saveOrUpdate(T entity);

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entitis a {@link java.util.Collection} object.
   */
  public void saveOrUpdate(Collection<T> entitis);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entitis a {@link java.util.Collection} object.
   */
  public void remove(Collection<T> entitis);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entity a T object.
   */
  public void remove(T entity);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param ids an array of ID objects.
   */
  public void remove(ID ids[]);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param id a ID object.
   */
  public void remove(ID id);

  /**
   * <p>
   * getEntityClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<T> getEntityClass();

}
