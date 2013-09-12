package org.beangle.commons.web.init;

import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

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
