/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

/**
 * <p>
 * ItemWriter interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface ItemWriter extends Writer {

  /**
   * <p>
   * write.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   */
  void write(Object obj);

  /**
   * <p>
   * writeTitle.
   * </p>
   * 
   * @param titleName a {@link java.lang.String} object.
   * @param data a {@link java.lang.Object} object.
   */
  void writeTitle(String titleName, Object data);

}
