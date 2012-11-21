/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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
