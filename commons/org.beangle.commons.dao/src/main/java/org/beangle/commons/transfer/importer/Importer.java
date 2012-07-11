/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer;

import java.util.Map;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.io.Reader;

/**
 * 数据转换接口
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Importer extends Transfer {
  /**
   * 是否忽略空值
   * 
   * @return a boolean.
   */
  public boolean ignoreNull();

  /**
   * 设置数据读取对象
   * 
   * @param reader a {@link org.beangle.commons.transfer.io.Reader} object.
   */
  public void setReader(Reader reader);

  /**
   * 获取reader
   * 
   * @return a {@link org.beangle.commons.transfer.io.Reader} object.
   */
  public Reader getReader();

  /**
   * 读取数据，设置内部步进状态等
   * 
   * @return a boolean.
   */
  public boolean read();

  /**
   * 当前读入的数据是否有效
   * 
   * @return a boolean.
   */
  public boolean isDataValid();

  /**
   * 设置当前正在转换的对象
   * 
   * @param object a {@link java.lang.Object} object.
   */
  public void setCurrent(Object object);

  /**
   * 返回现在正在转换的原始数据
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> getCurData();

  /**
   * 设置正在转换的对象
   * 
   * @param curData a {@link java.util.Map} object.
   */
  public void setCurData(Map<String, Object> curData);

  /**
   * <p>
   * getPrepare.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.importer.ImportPrepare} object.
   */
  public ImportPrepare getPrepare();

  /**
   * <p>
   * setPrepare.
   * </p>
   * 
   * @param prepare a {@link org.beangle.commons.transfer.importer.ImportPrepare} object.
   */
  public void setPrepare(ImportPrepare prepare);
}
