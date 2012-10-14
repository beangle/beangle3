/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import java.io.Serializable;
import java.util.List;

import org.beangle.commons.entity.Entity;

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
