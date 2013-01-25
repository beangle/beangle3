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
package org.beangle.commons.web.context;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.beangle.commons.inject.Container;
import org.beangle.commons.web.filter.LazyInitializingHook;

/**
 * @author chaostone
 * @since 3.1.0
 */
public final class ContainerUtils {
  public static final String RootContainer = "org.beangle.commons.web.context.RootContainer";
  public static final String LazyInitHooks = "org.beangle.commons.web.context.LazyInitHooks";

  public static List<LazyInitializingHook> getLazyInitialHooks(ServletContext servletContext) {
    @SuppressWarnings("unchecked")
    List<LazyInitializingHook> hooks = (List<LazyInitializingHook>) servletContext
        .getAttribute(LazyInitHooks);
    if (null == hooks) {
      hooks = new ArrayList<LazyInitializingHook>();
      servletContext.setAttribute(LazyInitHooks, hooks);
    }
    return hooks;
  }

  public static Container getContainer(ServletContext servletContext) {
    return (Container) servletContext.getAttribute(RootContainer);
  }
}
