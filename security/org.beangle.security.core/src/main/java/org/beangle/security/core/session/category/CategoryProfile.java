/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

/**
 * Session Profile for category User
 * 
 * @author chaostone
 * @since 2.4
 */
public interface CategoryProfile {

  /**
   * Distingish indentifier
   * 
   */
  Long getId();

  /**
   * User cateogory
   * 
   */
  String getCategory();

  /**
   * Whole capacity for a category user
   * 
   */
  int getCapacity();

  /**
   * Max session for same account
   * 
   */
  int getUserMaxSessions();

  /**
   * Max inactiveInterval in minutes
   * 
   */
  int getInactiveInterval();

}
