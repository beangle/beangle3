/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

/**
 * <p>
 * Scope class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public enum Scope {

  SINGLETON(""), PROTOTYPE("prototype"), REQUEST("request"), SESSION("session");

  String name;

  Scope(String name) {
    this.name = name;
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return name;
  }
}
