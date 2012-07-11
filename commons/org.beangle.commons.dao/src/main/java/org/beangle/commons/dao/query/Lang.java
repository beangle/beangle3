/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query;

/**
 * <p>
 * Lang class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public enum Lang {
  SQL("sql"), HQL("hql");
  private final String lang;

  private Lang(String name) {
    this.lang = name;
  }

  /**
   * <p>
   * Getter for the field <code>lang</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getLang() {
    return lang;
  }

}
