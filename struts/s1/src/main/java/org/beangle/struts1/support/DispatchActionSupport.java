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

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.WebUtils;

/**
 * Convenience class for Spring-aware Struts 1.1+ DispatchActions.
 *
 * <p>Provides a reference to the current Spring application context, e.g.
 * for bean lookup or resource loading. Auto-detects a ContextLoaderPlugIn
 * context, falling back to the root WebApplicationContext. For typical
 * usage, i.e. accessing middle tier beans, use a root WebApplicationContext.
 *
 * <p>For classic Struts Actions or Lookup/MappingDispatchActions, use the
 * analogous {@link ActionSupport ActionSupport} or
 * {@link LookupDispatchActionSupport LookupDispatchActionSupport} /
 * {@link MappingDispatchActionSupport MappingDispatchActionSupport} class,
 * respectively.
 *
 * <p>As an alternative approach, you can wire your Struts Actions themselves
 * as Spring beans, passing references to them via IoC rather than looking
 * up references in a programmatic fashion. Check out
 * {@link DelegatingActionProxy DelegatingActionProxy} and
 * {@link DelegatingRequestProcessor DelegatingRequestProcessor}.
 *
 * @author Juergen Hoeller
 * @since 1.0.1
 * @see ContextLoaderPlugIn#SERVLET_CONTEXT_PREFIX
 * @see org.springframework.web.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
 * @see org.springframework.web.context.ContextLoaderListener
 * @see ActionSupport
 * @see LookupDispatchActionSupport
 * @see MappingDispatchActionSupport
 * @see DelegatingActionProxy
 * @see DelegatingRequestProcessor
 * @deprecated as of Spring 3.0
 */
@Deprecated
public abstract class DispatchActionSupport extends DispatchAction {

  private WebApplicationContext webApplicationContext;

  private MessageSourceAccessor messageSourceAccessor;

  /**
   * Initialize the WebApplicationContext for this Action.
   * Invokes onInit after successful initialization of the context.
   * @see #initWebApplicationContext
   * @see #onInit
   */
  @Override
  public void setServlet(ActionServlet actionServlet) {
    super.setServlet(actionServlet);
    if (actionServlet != null) {
      this.webApplicationContext = initWebApplicationContext(actionServlet);
      this.messageSourceAccessor = new MessageSourceAccessor(this.webApplicationContext);
      onInit();
    }
    else {
      onDestroy();
    }
  }

  /**
   * Fetch ContextLoaderPlugIn's WebApplicationContext from the ServletContext,
   * falling back to the root WebApplicationContext (the usual case).
   * @param actionServlet the associated ActionServlet
   * @return the WebApplicationContext
   * @throws IllegalStateException if no WebApplicationContext could be found
   * @see DelegatingActionUtils#findRequiredWebApplicationContext
   */
  protected WebApplicationContext initWebApplicationContext(ActionServlet actionServlet)
      throws IllegalStateException {

    return DelegatingActionUtils.findRequiredWebApplicationContext(actionServlet, null);
  }

  /**
   * Return the current Spring WebApplicationContext.
   */
  protected final WebApplicationContext getWebApplicationContext() {
    return this.webApplicationContext;
  }

  /**
   * Return a MessageSourceAccessor for the application context
   * used by this object, for easy message access.
   */
  protected final MessageSourceAccessor getMessageSourceAccessor() {
    return this.messageSourceAccessor;
  }

  /**
   * Return the current ServletContext.
   */
  protected final ServletContext getServletContext() {
    return this.webApplicationContext.getServletContext();
  }

  /**
   * Return the temporary directory for the current web application,
   * as provided by the servlet container.
   * @return the File representing the temporary directory
   */
  protected final File getTempDir() {
    return WebUtils.getTempDir(getServletContext());
  }

  /**
   * Callback for custom initialization after the context has been set up.
   * @see #setServlet
   */
  protected void onInit() {
  }

  /**
   * Callback for custom destruction when the ActionServlet shuts down.
   * @see #setServlet
   */
  protected void onDestroy() {
  }

}
