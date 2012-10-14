/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import java.io.Serializable;
import java.util.Date;

import org.beangle.commons.entity.Entity;

/**
 * <p>
 * TimeEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface TimeEntity<T extends Serializable> extends Entity<T> {

  /**
   * Returns Created at.
   */
  public Date getCreatedAt();

  /**
   * <p>
   * setCreatedAt.
   * </p>
   * 
   * @param createdAt a {@link java.util.Date} object.
   */
  public void setCreatedAt(Date createdAt);

  /**
   * <p>
   * getUpdatedAt.
   * </p>
   * 
   * @return a {@link java.util.Date} object.
   */
  public Date getUpdatedAt();

  /**
   * <p>
   * setUpdatedAt.
   * </p>
   * 
   * @param updatedAt a {@link java.util.Date} object.
   */
  public void setUpdatedAt(Date updatedAt);
}
