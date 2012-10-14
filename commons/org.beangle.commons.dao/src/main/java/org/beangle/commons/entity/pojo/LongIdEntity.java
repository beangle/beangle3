/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import org.beangle.commons.entity.Entity;

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
  Long getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.Long} object.
   */
  void setId(Long id);
}
