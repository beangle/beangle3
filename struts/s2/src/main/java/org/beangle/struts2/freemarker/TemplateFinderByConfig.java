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
package org.beangle.struts2.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.beangle.struts2.convention.route.ViewMapper;

import freemarker.template.Configuration;

/**
 * Find template by configuration
 *
 * @author chaostone
 * @since 3.4.8
 */
public class TemplateFinderByConfig implements TemplateFinder {

  private final Configuration configuration;

  private final ViewMapper viewMapper;

  public TemplateFinderByConfig(Configuration configuration, ViewMapper viewMapper) {
    super();
    this.configuration = configuration;
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
      if (path.charAt(0) != '/') templateName = "/" + templateName;
      try {
        source = configuration.getTemplate(templateName);
      } catch (FileNotFoundException e) {
        // ignore
      } catch (IOException e) {
        source = "error ftl";
        break;
      }
      superClass = superClass.getSuperclass();
    } while (null == source && !superClass.equals(Object.class) && !superClass.isPrimitive());
    return (null == source) ? null : path;
  }
}
