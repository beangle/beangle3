/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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
  T get(ID id);

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param ids an array of ID objects.
   * @return a {@link java.util.List} object.
   */
  List<T> get(ID[] ids);

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entity a T object.
   */
  void saveOrUpdate(T entity);

  /**
   * <p>
   * saveOrUpdate.
   * </p>
   * 
   * @param entitis a {@link java.util.Collection} object.
   */
  void saveOrUpdate(Collection<T> entitis);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entitis a {@link java.util.Collection} object.
   */
  void remove(Collection<T> entitis);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param entity a T object.
   */
  void remove(T entity);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param ids an array of ID objects.
   */
  void remove(ID ids[]);

  /**
   * <p>
   * remove.
   * </p>
   * 
   * @param id a ID object.
   */
  void remove(ID id);

  /**
   * <p>
   * getEntityClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  Class<T> getEntityClass();

}
