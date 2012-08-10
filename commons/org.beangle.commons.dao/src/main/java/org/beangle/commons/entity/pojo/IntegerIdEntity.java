/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import org.beangle.commons.entity.Entity;

/**
 * <p>
 * IntegerIdEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: IntIdEntity.java Oct 25, 2011 8:32:11 AM chaostone $
 */
public interface IntegerIdEntity extends Entity<Integer> {

  /**
   * <p>
   * getId.
   * </p>
   * 
   * @return a {@link java.lang.Integer} object.
   */
  public Integer getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.Integer} object.
   */
  public void setId(Integer id);
}
