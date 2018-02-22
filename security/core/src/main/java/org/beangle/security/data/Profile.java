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
package org.beangle.security.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * 属性配置
 *
 * @author chaostone
 * @version $Id: Profile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface Profile extends Serializable {

  static final String AllValue = "*";

  Map<String, Object> getProperties();

  public default Object getProperty(String name) {
    return getProperties().get(name);
  }

  public default boolean matches(Profile other) {
    boolean matched = true;
    if (!other.getProperties().isEmpty()) {
      for (Map.Entry<String, Object> property : other.getProperties().entrySet()) {
        Object target = property.getValue();
        Object source = getProperty(property.getKey());
        if (null == source) {
          matched = false;
          break;
        }
        matched = Profile.AllValue.equals(source);
        if (matched) continue;

        if (!target.equals(Profile.AllValue)) {
          if (target instanceof Collection) {
            if (source instanceof Collection) {
              matched = ((Collection<?>) source).containsAll(((Collection<?>) target));
            }
          } else {
            Set<String> targetValues = CollectUtils.newHashSet(Strings.split(target.toString(), ","));
            Set<String> sourceValues = CollectUtils.newHashSet(Strings.split(source.toString(), ","));
            matched = sourceValues.containsAll(targetValues);
          }
        }
        if (!matched) break;
      }
    }
    return matched;
  }

}
