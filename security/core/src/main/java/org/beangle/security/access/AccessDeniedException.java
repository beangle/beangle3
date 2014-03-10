/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.security.access;

import org.beangle.security.BeangleSecurityException;

/**
 * 授权异常
 * 
 * @author chaostone
 */
public class AccessDeniedException extends BeangleSecurityException {
  private static final long serialVersionUID = -2403784040888146039L;

  private Object resource;

  public AccessDeniedException(Object resource, String message) {
    super(message);
    this.resource = resource;
  }

  public Object getResource() {
    return resource;
  }

}
