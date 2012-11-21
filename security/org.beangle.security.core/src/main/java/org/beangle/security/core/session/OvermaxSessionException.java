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
package org.beangle.security.core.session;

/**
 * Over max session limit Exception
 * 
 * @author chaostone
 */
public class OvermaxSessionException extends SessionException {
  private static final long serialVersionUID = -2827989849698493720L;

  int maxUserLimit;

  public OvermaxSessionException() {
    super();
  }

  public OvermaxSessionException(int maxUserLimit) {
    super(String.valueOf(maxUserLimit));
    this.maxUserLimit = maxUserLimit;
  }

  public int getMaxUserLimit() {
    return maxUserLimit;
  }

}
