/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.lang.Strings;

public class CharacterEncodingFilter implements Filter {

  protected String encoding = "utf-8";

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    request.setCharacterEncoding(encoding);
    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    String initEncoding = filterConfig.getInitParameter("encoding");
    if (Strings.isNotBlank(initEncoding)) {
      this.encoding = initEncoding;
    }
  }

  public void destroy() {
    encoding = null;
  }

}
