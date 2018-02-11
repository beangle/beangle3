/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.core.session.category;

import org.beangle.commons.event.Event;

/**
 * 会话配置的调整事件
 * 
 * @author chaostone
 * @version $Id: CategoryProfileModifyEvent.java Jul 14, 2011 7:52:50 AM chaostone $
 */
public class CategoryProfileUpdateEvent extends Event {

  private static final long serialVersionUID = 803263309728051161L;

  public CategoryProfileUpdateEvent(CategoryProfile source) {
    super(source);
  }

}
