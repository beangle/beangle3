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
package org.beangle.security.web.auth.logout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: LogoutHandlerStack.java Nov 22, 2010 7:34:38 PM chaostone $
 */
public class LogoutHandlerStack {

  private List<LogoutHandler> handlers = CollectUtils.newArrayList();

  public LogoutHandlerStack() {
    super();
  }

  public LogoutHandlerStack(LogoutHandler... initHandlers) {
    for (LogoutHandler handler : initHandlers)
      handlers.add(handler);
  }

  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
    for (LogoutHandler handler : handlers)
      handler.logout(request, response, auth);
  }

  public List<LogoutHandler> getHandlers() {
    return handlers;
  }

  public void setHandlers(List<LogoutHandler> handlers) {
    this.handlers = handlers;
  }

}
