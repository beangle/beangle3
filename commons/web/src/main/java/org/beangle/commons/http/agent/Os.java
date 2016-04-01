/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.http.agent;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

public class Os implements Serializable, Comparable<Os> {

  private static final long serialVersionUID = -7506270303767154240L;

  public static Map<String, Os> osMap = CollectUtils.newHashMap();
  public static final Os UNKNOWN = new Os(OsCategory.Unknown, null);

  public final OsCategory category;
  public final String version;

  private Os(OsCategory category, String version) {
    super();
    this.category = category;
    this.version = version;
  }

  @Override
  public String toString() {
    return category.getName() + " " + (version == null ? "" : version);
  }

  /**
   * Parses user agent string and returns the best match. Returns Os.UNKNOWN
   * if there is no match.
   * 
   * @param agentString
   * @return Os
   */
  public static Os parse(String agentString) {
    if (Strings.isEmpty(agentString)) { return Os.UNKNOWN; }
    for (OsCategory category : OsCategory.values()) {
      String version = category.match(agentString);
      if (version != null) {
        String key = category.getName() + "/" + version;
        Os os = osMap.get(key);
        if (null == os) {
          os = new Os(category, version);
          osMap.put(key, os);
        }
        return os;
      }
    }
    return Os.UNKNOWN;
  }

  public int compareTo(Os o) {
    return category.compareTo(o.category);
  }

}
