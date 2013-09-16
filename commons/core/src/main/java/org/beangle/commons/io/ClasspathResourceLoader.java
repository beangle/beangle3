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
package org.beangle.commons.io;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;

public class ClasspathResourceLoader implements ResourceLoader {

  /**
   * Store set of path prefixes to use with static resources.
   * Each prefix not ended with /
   */
  protected String[] pathPrefixes=new String[]{"/"};

  @Override
  public List<URL> loadAll(String resourceName) {
    List<URL> urls = CollectUtils.newArrayList();
    for (String pathPrefix : pathPrefixes)
      urls.addAll(ClassLoaders.getResources(pathPrefix + resourceName, getClass()));
    return urls;
  }

  @Override
  public Option<URL> load(String name) {
    URL url = null;
    for (String pathPrefix : pathPrefixes) {
      url = ClassLoaders.getResource(pathPrefix + name, getClass());
      if (url != null) break;
    }
    if (null == url) return Option.none();
    else return Option.some(url);
  }

  @Override
  public List<URL> load(List<String> names) {
    List<URL> urls = CollectUtils.newArrayList(names.size());
    for (String name : names) {
      Option<URL> url = load(name);
      if (!url.isEmpty()) urls.add(url.get());
    }
    return urls;
  }

  public void setPrefixes(String prefixes) {
    if (Strings.isEmpty(prefixes)) pathPrefixes = new String[] { "/" };
    else this.pathPrefixes = Strings.split(prefixes, " ");
  }
}
