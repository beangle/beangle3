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
package org.beangle.struts2.dispatcher;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.servlet.ServletHostConfig;

import com.opensymphony.xwork2.ActionContext;

/**
 * Server Struts2 Action Only.
 * 
 * @author chaostone
 */
public class ActionServlet extends HttpServlet {

  private static final long serialVersionUID = -2962412407855583998L;
  private PrepareOperations prepare;
  private ExecuteOperations execute;

  @Override
  public void init(ServletConfig filterConfig) throws ServletException {
    InitOperations init = new InitOperations();
    try {
      ServletHostConfig config = new ServletHostConfig(filterConfig);
      init.initLogging(config);
      ActionContext.setContext(new ActionContext(new HashMap<String,Object>()));
      ServletActionContext.setServletContext(config.getServletContext());
      Dispatcher dispatcher = init.initDispatcher(config);
      init.initStaticContentLoader(config, dispatcher);
      ActionContext.setContext(null);
      prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
      execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
      
    } finally {
      init.cleanup();
      
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    try {
      prepare.createActionContext(request, response);
      prepare.assignDispatcherToThread();
      // prepare.setEncodingAndLocale(request, response);
      HttpServletRequest oldRequest = request;
      request = prepare.wrapRequest(request);
      // Dislike Struts2 optimization for jstl .It delegate request.getAttribute to ongl valuestack.
      // When use freemarker,it was not nessesary.
      if (!(request instanceof MultiPartRequestWrapper)) request = oldRequest;
      ActionMapping mapping = prepare.findActionMapping(request, response);
      if (mapping == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        execute.executeAction(request, response, mapping);
      }
    } finally {
      prepare.cleanupRequest(request);
    }
  }

  @Override
  public void destroy() {
    prepare.cleanupDispatcher();
  }
}
