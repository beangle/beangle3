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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load resource by class loader.
 * 
 * @author chaostone
 * @since 3.3.0
 */
public class ClassResourceLoader implements ResourceLoader {

  final List<ClassLoader> loaders;

  private static final Logger logger = LoggerFactory.getLogger(ClassResourceLoader.class);

  public ClassResourceLoader(ClassLoader... loaders) {
    super();
    this.loaders = CollectUtils.newArrayList();
    for (ClassLoader loader : loaders)
      if (null != loader) this.loaders.add(loader);
  }

  @Override
  public Option<URL> getResource(String resourceName) {
    for (ClassLoader loader : loaders) {
      URL url = loader.getResource(resourceName);
      if (null != url) return Option.some(url);
    }
    return Option.none();
  }

  @Override
  public List<URL> getResources(String resourceName) {
    Enumeration<URL> em = null;

    for (ClassLoader loader : loaders) {
      try {
        em = loader.getResources(resourceName);
        if (em.hasMoreElements()) break;
      } catch (IOException e) {
        logger.error("cannot getResources " + resourceName, e);
      }
    }
    List<URL> urls = CollectUtils.newArrayList();
    while (null != em && em.hasMoreElements())
      urls.add(em.nextElement());
    return urls;
  }

}
