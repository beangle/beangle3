/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.inject.spring.config;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.Strings;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ResourcesEditor extends PropertyEditorSupport {

  private final ResourceLoader resourceLoader;

  private final PathMatchingResourcePatternResolver resourcePatternResolver;

  public ResourcesEditor() {
    super();
    resourceLoader = new DefaultResourceLoader();
    resourcePatternResolver = new PathMatchingResourcePatternResolver();
  }

  private URL getResource(String location) {
    if (Strings.isBlank(location)) return null;
    Resource res = resourceLoader.getResource(location);
    try {
      return res.exists() ? res.getURL() : null;
    } catch (IOException e) {
    }
    return null;
  }

  private List<URL> getResources(String locationPattern) {
    List<URL> locals = CollectUtils.newArrayList();
    try {
      Resource[] reses = resourcePatternResolver.getResources(locationPattern);
      for (Resource res : reses)
        if (res.exists()) locals.add(res.getURL());
    } catch (IOException e) {
    }
    return locals;
  }

  public void setAsText(String text) {
    if (Strings.isNotBlank((String) text)) {
      Resources resources = new Resources();
      String[] paths = text.split(";");
      if (paths.length > 0) resources.setGlobal(getResource(paths[0]));
      if (paths.length > 1) resources.setLocals(getResources(paths[1]));
      if (paths.length > 2) resources.setUser(getResource(paths[2]));
      setValue(resources);
    } else {
      setValue(null);
    }
  }
}
