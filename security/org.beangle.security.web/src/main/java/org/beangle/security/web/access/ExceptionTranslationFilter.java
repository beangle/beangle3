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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.security.BeangleSecurityException;
import org.beangle.security.Securities;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.beangle.security.web.util.ThrowableAnalyzer;
import org.beangle.security.web.util.ThrowableCauseExtractor;

public class ExceptionTranslationFilter extends GenericHttpFilter {

  private AccessDeniedHandler accessDeniedHandler;
  private AuthenticationEntryPoint authenticationEntryPoint;
  private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

  protected void initFilerBean() throws ServletException {
    Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint must be specified");
    Assert.notNull(throwableAnalyzer, "throwableAnalyzer must be specified");
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (IOException ex) {
      throw ex;
    } catch (Exception ex) {
      // Try to extract a BeangleSecurityException from the stacktrace
      Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
      BeangleSecurityException ase = (BeangleSecurityException) this.throwableAnalyzer
          .getFirstThrowableOfType(BeangleSecurityException.class, causeChain);

      if (ase != null) {
        handleException(request, response, chain, ase);
      } else {
        // Rethrow ServletExceptions and RuntimeExceptions as-is
        if (ex instanceof ServletException) {
          throw (ServletException) ex;
        } else if (ex instanceof RuntimeException) { throw (RuntimeException) ex; }

        // Wrap other Exceptions. These are not expected to happen
        throw new RuntimeException(ex);
      }
    }
  }

  private void handleException(ServletRequest request, ServletResponse response, FilterChain chain,
      BeangleSecurityException exception) throws IOException, ServletException {
    if (exception instanceof AuthenticationException) {
      logger.debug("Authentication exception occurred", exception);
      sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
    } else if (exception instanceof AccessDeniedException) {
      AccessDeniedException ae = (AccessDeniedException) exception;
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (Securities.isValid(auth)) {
        logger.debug("{} access {} is denied", auth.getName(), ae.getResource());
        accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
      } else {
        logger.debug("anonymous access {} is denied", ae.getResource());
        sendStartAuthentication(request, response, chain, new AuthenticationException(ae.getMessage()));
      }
    }
  }

  protected void sendStartAuthentication(ServletRequest request, ServletResponse response, FilterChain chain,
      AuthenticationException reason) throws ServletException, IOException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    SecurityContextHolder.getContext().setAuthentication(null);
    authenticationEntryPoint.commence(httpRequest, response, reason);
  }

  public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
    Assert.notNull(accessDeniedHandler, "AccessDeniedHandler required");
    this.accessDeniedHandler = accessDeniedHandler;
  }

  public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
    this.throwableAnalyzer = throwableAnalyzer;
  }

  /**
   * Default implementation of <code>ThrowableAnalyzer</code> which is capable
   * of also unwrapping <code>ServletException</code>s.
   */
  private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
    /**
     * @see org.beangle.security.web.util.ThrowableAnalyzer#initExtractorMap()
     */
    protected void initExtractorMap() {
      super.initExtractorMap();
      registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
        public Throwable extractCause(Throwable throwable) {
          ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
          return ((ServletException) throwable).getRootCause();
        }
      });
    }

  }

}
