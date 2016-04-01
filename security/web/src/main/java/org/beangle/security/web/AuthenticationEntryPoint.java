/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.security.core.AuthenticationException;

public interface AuthenticationEntryPoint {

  /**
   * Commences an authentication scheme.
   * <p>
   * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code> attribute
   * named <code>AbstractProcessingFilter.SECURITY_SAVED_REQUEST_KEY</code> with the requested
   * target URL before calling this method.
   * </p>
   * <p>
   * Implementations should modify the headers on the <code>ServletResponse</code> as necessary to
   * commence the authentication process.
   * </p>
   * 
   * @param request
   *          that resulted in an <code>AuthenticationException</code>
   * @param response
   *          so that the user agent can begin authentication
   * @param authException
   *          that caused the invocation
   */
  void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
      throws IOException, ServletException;
}
