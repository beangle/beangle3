/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
