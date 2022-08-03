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
package org.beangle.ems.app.security;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.Conditions;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.security.core.userdetail.Profile;
import org.beangle.security.data.Permission;

import java.util.Arrays;
import java.util.List;

public final class SecurityUtils {

  public static void apply(OqlBuilder<?> builder, Permission permission, Profile... profiles) {
    apply(builder, permission, Arrays.asList(profiles));
  }

  public static void apply(OqlBuilder<?> query, Permission permission, List<Profile> profiles) {
    List<Object> paramValues = CollectUtils.newArrayList();
    // 处理限制对应的模式
    if (Strings.isEmpty(permission.getFilters())) return;

    String patternContent = permission.getFilters();
    patternContent = Strings.replace(patternContent, "{alias}", query.getAlias());
    String[] contents = Strings.split(Strings.replace(patternContent, " and ", "$"), "$");

    List<Condition> conditions = CollectUtils.newArrayList();
    for (Profile profile : profiles) {
      StringBuilder conBuilder = new StringBuilder("(");
      for (int i = 0; i < contents.length; i++) {
        String content = contents[i];
        Condition c = new Condition(content);
        List<String> params = c.getParamNames();
        for (final String paramName : params) {
          Object value;
          if (paramName.endsWith("s")) {
            value = profile.getProperty(paramName.substring(0, paramName.length() - 1));
          } else {
            value = profile.getProperty(paramName);
          }
          if (null != value) {
            if (value.equals(Profile.AllValue)) {
              content = "";
            } else {
              paramValues.add(value);
            }
          } else {
            throw new RuntimeException(paramName + " had not been initialized");
          }
        }
        if (conBuilder.length() > 1 && content.length() > 0) conBuilder.append(" and ");
        conBuilder.append(content);
      }

      if (conBuilder.length() > 1) {
        conBuilder.append(')');
        Condition con = new Condition(conBuilder.toString());
        con.params(paramValues);
        conditions.add(con);
      }
    }
    if (!conditions.isEmpty()) query.where(Conditions.or(conditions));
  }

}
