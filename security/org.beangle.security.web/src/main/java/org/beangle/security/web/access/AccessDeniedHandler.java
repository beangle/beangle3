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
package org.beangle.security.web.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.security.access.AccessDeniedException;

/**
 * @author chaostone
 * @since 2.4
 */
public interface AccessDeniedHandler {

  /**
   * Handles an access denied failure.
   * 
   * @param request that resulted in an <code>AccessDeniedException</code>
   * @param responsenso that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   * @throws IOException in the event of an IOException
   * @throws ServletException in the event of a ServletException
   */
  void handle(ServletRequest request, ServletResponse response, AccessDeniedException accessDeniedException)
      throws IOException, ServletException;
}
