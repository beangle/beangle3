/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.entity;

import java.io.Serializable;
import java.util.List;

import org.beangle.commons.dao.Entity;

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
   * @param <T> a T object.
   * @param <ID> a ID object.
   * @return a T object.
   */
  public T getParent();

  /**
   * <p>
   * setParent.
   * </p>
   * 
   * @param parent a T object.
   */
  public void setParent(T parent);

  /**
   * <p>
   * getChildren.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<T> getChildren();

  /**
   * <p>
   * setChildren.
   * </p>
   * 
   * @param children a {@link java.util.List} object.
   */
  public void setChildren(List<T> children);
}
