/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.filter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

/**
 * Once per request filter.
 * 
 * @author chaostone
 * @since 3.0.0
 */
public abstract class OncePerRequestFilter extends GenericHttpFilter {

  private String filteredAttributeName;

  public boolean firstEnter(ServletRequest request) {
    if (null != request.getAttribute(filteredAttributeName)) return false;
    else {
      request.setAttribute(filteredAttributeName, Boolean.TRUE);
      return true;
    }
  }

  @Override
  protected void initFilterBean() throws ServletException {
    String name = getFilterName();
    if (name == null) name = getClass().getName();
    filteredAttributeName = name + ".FILTED";
  }

}
