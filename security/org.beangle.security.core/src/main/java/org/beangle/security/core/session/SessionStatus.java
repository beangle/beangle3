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

import java.util.Date;

/**
 * 会话状态
 * 
 * @author chaostone
 * @version $Id: SessionStatus.java Jul 14, 2011 7:11:39 PM chaostone $
 */
public final class SessionStatus {

  final String username;

  final Date expiredAt;

  public SessionStatus(Sessioninfo info) {
    super();
    this.username = info.getUsername();
    this.expiredAt = info.getExpiredAt();
  }

  public SessionStatus(String username, Date expiredAt) {
    super();
    this.username = username;
    this.expiredAt = expiredAt;
  }

  public String getUsername() {
    return username;
  }

  public Date getExpiredAt() {
    return expiredAt;
  }

  public boolean isExpired() {
    return null != expiredAt;
  }
}
