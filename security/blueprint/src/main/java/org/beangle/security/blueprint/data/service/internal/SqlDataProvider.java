/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint.data.service.internal;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.SqlBuilder;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.data.service.UserDataProvider;

public class SqlDataProvider extends BaseServiceImpl implements UserDataProvider {

  @SuppressWarnings("unchecked")
  public <T> List<T> getData(Field field, String source, Object... keys) {
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
