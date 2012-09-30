/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.dispatcher;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.servlet.ServletHostConfig;

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
      Dispatcher dispatcher = init.initDispatcher(config);
      init.initStaticContentLoader(config, dispatcher);

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
      //prepare.setEncodingAndLocale(request, response);
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
