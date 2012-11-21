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
  String[] readDescription();

  /**
   * <p>
   * readTitle.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  String[] readTitle();

  /**
   * <p>
   * setDataIndex.
   * </p>
   * 
   * @param dataIndex a int.
   */
  void setDataIndex(int dataIndex);

  /**
   * <p>
   * getDataIndex.
   * </p>
   * 
   * @return a int.
   */
  int getDataIndex();

  /**
   * <p>
   * setHeadIndex.
   * </p>
   * 
   * @param headIndex a int.
   */
  void setHeadIndex(int headIndex);

  /**
   * <p>
   * getHeadIndex.
   * </p>
   * 
   * @return a int.
   */
  int getHeadIndex();
}
