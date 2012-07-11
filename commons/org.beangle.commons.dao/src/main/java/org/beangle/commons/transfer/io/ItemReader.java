/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

/**
 * <p>
 * ItemReader interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface ItemReader extends Reader {

  /**
   * <p>
   * readDescription.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readDescription();

  /**
   * <p>
   * readTitle.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readTitle();

  /**
   * <p>
   * setDataIndex.
   * </p>
   * 
   * @param dataIndex a int.
   */
  public void setDataIndex(int dataIndex);

  /**
   * <p>
   * getDataIndex.
   * </p>
   * 
   * @return a int.
   */
  public int getDataIndex();

  /**
   * <p>
   * setHeadIndex.
   * </p>
   * 
   * @param headIndex a int.
   */
  public void setHeadIndex(int headIndex);

  /**
   * <p>
   * getHeadIndex.
   * </p>
   * 
   * @return a int.
   */
  public int getHeadIndex();
}
