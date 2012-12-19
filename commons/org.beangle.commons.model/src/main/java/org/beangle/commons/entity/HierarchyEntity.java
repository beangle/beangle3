/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * HierarchyEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface HierarchyEntity<T, ID extends Serializable> extends Entity<ID> {

  /**
   * Return index no
   */
  String getIndexno();

  /**
   * <p>
   * getParent.
   * </p>
   * 
   * @return a T object.
   */
  T getParent();

  /**
   * <p>
   * setParent.
   * </p>
   * 
   * @param parent a T object.
   */
  void setParent(T parent);

  /**
   * <p>
   * getChildren.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  List<T> getChildren();

  /**
   * <p>
   * setChildren.
   * </p>
   * 
   * @param children a {@link java.util.List} object.
   */
  void setChildren(List<T> children);

}
