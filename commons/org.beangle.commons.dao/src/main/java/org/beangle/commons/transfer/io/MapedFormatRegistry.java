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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * MapedFormatRegistry class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MapedFormatRegistry implements TransferFormatRegistry {

  Map<String, Class<? extends Writer>> writers = CollectUtils.newHashMap();

  Map<String, Class<? extends Reader>> readers = CollectUtils.newHashMap();

  /** {@inheritDoc} */
  public Writer getWriter(String format) {
    Class<? extends Writer> writerClass = writers.get(format);
    if (null == writerClass) { return null; }
    try {
      return writerClass.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  public Reader getReader(String format) {
    Class<? extends Reader> readerClass = readers.get(format);
    if (null == readerClass) { return null; }
    try {
      return readerClass.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  public <T extends Writer> void registerWriter(String format, Class<T> writerClazz) {
    writers.put(format, writerClazz);
  }

  /** {@inheritDoc} */
  public <T extends Reader> void registerReader(String format, Class<T> readerClazz) {
    readers.put(format, readerClazz);
  }

  /** {@inheritDoc} */
  public void unRegisterWriter(String format) {
    writers.remove(format);
  }

  /** {@inheritDoc} */
  public void unRegisterReader(String format) {
    readers.remove(format);
  }

}
