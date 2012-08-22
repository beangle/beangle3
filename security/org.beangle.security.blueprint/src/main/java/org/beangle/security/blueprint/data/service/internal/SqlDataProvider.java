/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service.internal;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.SqlBuilder;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.service.UserDataProvider;

public class SqlDataProvider extends BaseServiceImpl implements UserDataProvider {

  @SuppressWarnings("unchecked")
  public <T> List<T> getData(ProfileField field, String source,Object... keys) {
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
