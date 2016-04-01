/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.io.OutputStream;

import org.beangle.commons.transfer.exporter.Context;

/**
 * <p>
 * Writer interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Writer {

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  TransferFormat getFormat();

  /**
   * <p>
   * setContext.
   * </p>
   * 
   * @param context a {@link org.beangle.commons.transfer.exporter.Context} object.
   */
  void setContext(Context context);

  /**
   * <p>
   * getOutputStream.
   * </p>
   * 
   * @return a {@link java.io.OutputStream} object.
   */
  OutputStream getOutputStream();

  /**
   * <p>
   * setOutputStream.
   * </p>
   * 
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  void setOutputStream(OutputStream outputStream);

  /**
   * <p>
   * close.
   * </p>
   */
  void close();
}
