/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.entity;

import org.beangle.commons.dao.Entity;

/**
 * <p>
 * StringIdEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: StringIdEntity.java Jul 15, 2011 7:58:42 AM chaostone $
 */
public interface StringIdEntity extends Entity<String> {

  /**
   * <p>
   * getId.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.String} object.
   */
  public void setId(String id);

}
