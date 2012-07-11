/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer;

/**
 * <p>
 * SimpleItemImporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SimpleItemImporter extends AbstractItemImporter {

  /** {@inheritDoc} */
  public void setCurrent(Object object) {

  }

  /**
   * <p>
   * getCurrent.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent() {
    return getCurData();
  }

  /**
   * <p>
   * getDataName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getDataName() {
    return getCurData().getClass().getName();
  }

  /**
   * <p>
   * transferItem.
   * </p>
   */
  public void transferItem() {
  }
}
