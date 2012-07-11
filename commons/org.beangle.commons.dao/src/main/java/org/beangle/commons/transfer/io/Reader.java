/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

/**
 * 数据读取类
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Reader {

  /**
   * 读取数据
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object read();

  /**
   * 返回读取类型的格式
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getFormat();
}
