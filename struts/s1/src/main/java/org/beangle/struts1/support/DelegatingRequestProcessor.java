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
package org.beangle.struts1.support;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ModuleConfig;
import org.beangle.inject.spring.web.ContextLoader;
import org.springframework.context.ApplicationContext;

/**
 * Subclass of Struts's default {@link RequestProcessor} that looks up
 * Spring-managed Struts {@link Action Actions} defined in {@link ApplicationContext}
 * (or, as a fallback, in the root {@code ApplicationContext}).
 */
public class DelegatingRequestProcessor extends RequestProcessor {

  private ApplicationContext context;

  @Override
  public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
    super.init(actionServlet, moduleConfig);
    if (actionServlet != null) {
      context = (ApplicationContext) actionServlet.getServletContext()
          .getAttribute(ContextLoader.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }
  }

  @Override
  protected Action processActionCreate(HttpServletRequest request, HttpServletResponse response,
      ActionMapping mapping) throws IOException {
    String beanName = determineActionBeanName(mapping);
    Action action = (Action) context.getBean(beanName);
    if (action != null) { return action; }
    return super.processActionCreate(request, response, mapping);
  }

  @Override
  protected HttpServletRequest processMultipart(HttpServletRequest request) {
    return request;
  }

  public static String determineActionBeanName(ActionMapping mapping) {
    String prefix = mapping.getModuleConfig().getPrefix();
    String path = mapping.getPath();
    return prefix + path;
  }
}
