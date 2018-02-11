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
package org.beangle.commons.entity.event;

import java.util.List;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.event.Event;

/**
 * 实体操作相关事件
 *
 * @author chaostone
 * @version $Id: EntityOperationEvent.java Mar 3, 2012 9:05:40 PM chaostone $
 */
@SuppressWarnings("serial")
public abstract class AbstractEntityEvent<T extends Entity<?>> extends Event {

  private final Class<T> clazz;

  public AbstractEntityEvent(Class<T> clazz, List<T> source) {
    super(source);
    this.clazz = clazz;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  @SuppressWarnings("unchecked")
  public List<T> getEntities() {
    return (List<T>) source;
  }
}
