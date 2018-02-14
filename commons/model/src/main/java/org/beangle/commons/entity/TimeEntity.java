/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.entity;

import java.util.Date;

/**
 * <p>
 * TimeEntity interface.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public interface TimeEntity {

  /**
   * <p>
   * getUpdatedAt.
   * </p>
   *
   * @return a {@link java.util.Date} object.
   */
  Date getUpdatedAt();

  /**
   * <p>
   * setUpdatedAt.
   * </p>
   *
   * @param updatedAt a {@link java.util.Date} object.
   */
  void setUpdatedAt(Date updatedAt);
}
