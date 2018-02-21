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
package org.beangle.security.blueprint.service.impl;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.SqlBuilder;
import org.beangle.security.blueprint.model.Dimension;
import org.beangle.security.blueprint.service.UserDataProvider;

public class SqlDataProvider extends BaseServiceImpl implements UserDataProvider {

  @SuppressWarnings("unchecked")
  public <T> List<T> getData(Dimension field, String source, Object... keys) {
    try {
      return (List<T>) entityDao.search(SqlBuilder.sql(source));
    } catch (Exception e) {
      logger.error("Get data error", e);
    }
    return Collections.emptyList();
  }

  public String getName() {
    return "oql";
  }

}
