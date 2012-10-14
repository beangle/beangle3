/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.pojo;

import org.beangle.commons.entity.Entity;

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
  String getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.String} object.
   */
  void setId(String id);

}
