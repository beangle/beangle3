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
package org.beangle.security.blueprint.model;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * 属性配置
 *
 * @author chaostone
 * @version $Id: Profile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface Profile {

  static final String AllValue = "*";

  String getProperty(Dimension field);

  String getProperty(String name);

  public default boolean matches(Profile other) {
    boolean matched = true;
    if (!other.getProperties().isEmpty()) {
      for (Map.Entry<Dimension, String> property : other.getProperties().entrySet()) {
        String target = property.getValue();
        String op = getProperty(property.getKey());
        String source = "";
        if (null != op) source = op;
        if (target.equals(Profile.AllValue)) {
          matched = source.equals(Profile.AllValue);
        } else {
          matched = CollectUtils.newHashSet(Strings.split(source, ","))
              .contains(CollectUtils.newHashSet(Strings.split(target, ",")));
        }
        if (!matched) break;
      }
    }
    return matched;
  }

  Map<Dimension, String> getProperties();
}
