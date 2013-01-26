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

import java.io.Serializable;

/**
 * 会话状态
 * 
 * @author chaostone
 * @version $Id: SessionStatus.java Jul 14, 2011 7:11:39 PM chaostone $
 */
public final class SessionStatus implements Serializable {

  private static final long serialVersionUID = -1110252524091983477L;

  final String username;

  long lastAccessedTime;

  public SessionStatus(Sessioninfo info) {
    super();
    this.username = info.getUsername();
    this.lastAccessedTime = (null == info.getLastAccessAt()) ? -1 : info.getLastAccessAt().getTime();
  }

  public SessionStatus(String username) {
    super();
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public long getLastAccessedTime() {
    return lastAccessedTime;
  }

  public void setLastAccessedTime(long lastAccessedTime) {
    this.lastAccessedTime = lastAccessedTime;
  }

}
