/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint.event;

import java.util.List;

import org.beangle.commons.event.Event;
import org.beangle.security.blueprint.User;

/**
 * 用户变动事件
 * 
 * @author chaostone
 * @version $Id: UserEvent.java Jul 27, 2011 10:11:20 AM chaostone $
 */
public class UserEvent extends Event {
  private static final long serialVersionUID = -2213942260473001852L;

  public UserEvent(List<? extends User> users) {
    super(users);
    setResource("用户管理");
  }

  @SuppressWarnings("unchecked")
  public List<? extends User> getUsers() {
    return (List<? extends User>) source;
  }

  public String getUserNames() {
    StringBuilder sb = new StringBuilder();
    for (User user : getUsers()) {
      sb.append(user.getName()).append('(').append(user.getFullname()).append("),");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
