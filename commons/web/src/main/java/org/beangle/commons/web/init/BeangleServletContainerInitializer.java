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
package org.beangle.commons.web.init;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

@HandlesTypes(StartupInitializer.class)
public class BeangleServletContainerInitializer implements ServletContainerInitializer {
  public void onStartup(Set<Class<?>> initializerClasses, ServletContext servletContext)
      throws ServletException {
    List<StartupInitializer> initializers = new LinkedList<StartupInitializer>();
    if (initializerClasses != null) {
      for (Class<?> waiClass : initializerClasses) {
        if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers())
            && StartupInitializer.class.isAssignableFrom(waiClass)) {
          try {
            initializers.add((StartupInitializer) waiClass.newInstance());
          } catch (Throwable ex) {
            throw new ServletException("Failed to instantiate StartupInitializer class", ex);
          }
        }
      }
    }

    if (initializers.isEmpty()) {
      servletContext.log("No Beangle StartupInitializer types detected on classpath");
      return;
    }

    servletContext.log("Beangle StartupInitializer detected on classpath: " + initializers);

    for (StartupInitializer initializer : initializers) {
      initializer.onStartup(servletContext);
    }
  }
}
