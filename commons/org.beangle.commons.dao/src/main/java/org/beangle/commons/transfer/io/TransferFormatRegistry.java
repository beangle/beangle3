/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.io;

/**
 * 导出writer注册表
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface TransferFormatRegistry {

  /**
   * <p>
   * getWriter.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.transfer.io.Writer} object.
   */
  Writer getWriter(String format);

  /**
   * <p>
   * getReader.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.transfer.io.Reader} object.
   */
  Reader getReader(String format);

  /**
   * <p>
   * registerWriter.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   * @param writerClazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   */
  <T extends Writer> void registerWriter(String format, Class<T> writerClazz);

  /**
   * <p>
   * registerReader.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   * @param readerClazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   */
  <T extends Reader> void registerReader(String format, Class<T> readerClazz);

  /**
   * <p>
   * unRegisterWriter.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   */
  void unRegisterWriter(String format);

  /**
   * <p>
   * unRegisterReader.
   * </p>
   * 
   * @param format a {@link java.lang.String} object.
   */
  void unRegisterReader(String format);
}
