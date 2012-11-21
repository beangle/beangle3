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
package org.beangle.commons.entity.pojo;

import org.beangle.commons.entity.Entity;

/**
 * <p>
 * StringIdEntity interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: StringIdEntity.java Jul 15, 2011 7:58:42 AM chaostone $
 */
public interface StringIdEntity extends Entity<String> {

  /**
   * <p>
   * getId.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  String getId();

  /**
   * <p>
   * setId.
   * </p>
   * 
   * @param id a {@link java.lang.String} object.
   */
  void setId(String id);

}
