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
package org.beangle.struts2.freemarker;

import java.io.IOException;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.convention.route.ViewMapper;

import freemarker.cache.TemplateLoader;

/**
 * Find template by tempalte loader.
 * 
 * @author chaostone
 * @since 3.4.8
 */
public class TemplateFinderByLoader implements TemplateFinder {

  private final TemplateLoader loader;

  private final ViewMapper viewMapper;

  private Set<String> missings = CollectUtils.newHashSet();

  public TemplateFinderByLoader(TemplateLoader loader, ViewMapper viewMapper) {
    super();
    this.loader = loader;
    this.viewMapper = viewMapper;
  }

  public String find(Class<?> actionClass, String method, String viewName, String extention) {
    String path = null;
    Class<?> superClass = actionClass;
    Object source = null;
    do {
      StringBuilder buf = new StringBuilder();
      buf.append(viewMapper.getViewPath(superClass.getName(), method, viewName));
      buf.append('.').append(extention);
      path = buf.toString();
      String templateName = path;
      if (path.charAt(0) == '/') templateName = templateName.substring(1);
      if (!missings.contains(templateName)) {
        try {
          source = loader.findTemplateSource(templateName);
          if (null != source) loader.closeTemplateSource(source);
          else missings.add(templateName);
        } catch (IOException e) {
          break;
        }
      }
      superClass = superClass.getSuperclass();
    } while (null == source && !superClass.equals(Object.class) && !superClass.isPrimitive());
    return (null == source) ? null : path;
  }

}
