/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.transfer.exporter;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.io.Writer;

/**
 * 数据导出转换器
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Exporter extends Transfer {

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
   * setWriter.
   * </p>
   * 
   * @param writer a {@link org.beangle.commons.transfer.io.Writer} object.
   */
  void setWriter(Writer writer);

  /**
   * <p>
   * getWriter.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.io.Writer} object.
   */
  Writer getWriter();
}
