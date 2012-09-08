/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

public interface CategoryProfile {

  Long getId();
  
  String getCategory();

  int getCapacity();

  int getUserMaxSessions();

  int getInactiveInterval();

}
