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
