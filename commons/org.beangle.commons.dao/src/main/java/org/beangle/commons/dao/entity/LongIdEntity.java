/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.entity;

import org.beangle.commons.dao.Entity;

/**
 * <p>
 * LongIdEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface LongIdEntity extends Entity<Long> {

  /**
   * <p>
   * getId.
   * </p>
   * 
   * @return a {@link java.lang.Long} object.
   */
  public Long getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.Long} object.
   */
  public void setId(Long id);
}
