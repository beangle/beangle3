/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.web.resource.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.resource.PathResolver;

public class PathResolverImpl implements PathResolver {
  
  public List<String> resolve(String path) {
    String lastPostfix = "." + Strings.substringAfterLast(path, ".");
    String[] names = Strings.split(path, ",");
    List<String> rs = CollectUtils.newArrayList(names.length);
    String pathDir = null;
    for (String name : names) {
      if (name.startsWith("/")) pathDir = Strings.substringBeforeLast(name, "/");
      else name = pathDir + "/" + name;
      if (!name.endsWith(lastPostfix)) name += lastPostfix;
      rs.add(name);
    }
    return rs;
  }
}
