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
import org.apache.struts2.dispatcher.ExecuteOperations;
import org.apache.struts2.dispatcher.InitOperations;
import org.apache.struts2.dispatcher.PrepareOperations;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.servlet.ServletHostConfig;

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
  public void init(ServletConfig sc) throws ServletException {
    InitOperations init = new InitOperations();
    try {
      ServletHostConfig config = new ServletHostConfig(sc);
      init.initLogging(config);
      ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
      ServletActionContext.setServletContext(config.getServletContext());
      Dispatcher dispatcher = init.initDispatcher(config);
      init.initStaticContentLoader(config, dispatcher);
      ActionContext.setContext(null);
      prepare = new PrepareOperations(dispatcher);
      execute = new ExecuteOperations(dispatcher);
    } finally {
      init.cleanup();
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
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
