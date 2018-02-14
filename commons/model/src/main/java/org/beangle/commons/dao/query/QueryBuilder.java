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
package org.beangle.commons.dao.query;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;

/**
 * <p>
 * QueryBuilder interface.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public interface QueryBuilder<T> {

  /**
   * <p>
   * build.
   * </p>
   *
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  Query<T> build();

  /**
   * <p>
   * limit.
   * </p>
   *
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   * @return a {@link org.beangle.commons.dao.query.QueryBuilder} object.
   */
  QueryBuilder<T> limit(PageLimit limit);

  /**
   * <p>
   * getParams.
   * </p>
   *
   * @return a {@link java.util.Map} object.
   */
  Map<String, Object> getParams();

  /**
   * <p>
   * params.
   * </p>
   *
   * @param newParams a {@link java.util.Map} object.
   * @return a {@link org.beangle.commons.dao.query.QueryBuilder} object.
   */
  QueryBuilder<T> params(Map<String, Object> newParams);
}
