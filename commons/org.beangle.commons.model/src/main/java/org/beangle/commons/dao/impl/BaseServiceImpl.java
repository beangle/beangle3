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
package org.beangle.commons.dao.impl;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventMulticaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract BaseServiceImpl class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class BaseServiceImpl {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected EntityDao entityDao;

  protected EventMulticaster eventMulticaster;

  /**
   * <p>
   * Getter for the field <code>entityDao</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.EntityDao} object.
   */
  public EntityDao getEntityDao() {
    return entityDao;
  }

  /**
   * <p>
   * Setter for the field <code>entityDao</code>.
   * </p>
   * 
   * @param entityDao a {@link org.beangle.commons.dao.EntityDao} object.
   */
  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  /**
   * <p>
   * publish.
   * </p>
   * 
   * @param e a {@link org.beangle.commons.event.Event} object.
   */
  public void publish(Event e) {
    if (null != eventMulticaster) eventMulticaster.multicast(e);
  }

  /**
   * <p>
   * Getter for the field <code>eventMulticaster</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.event.EventMulticaster} object.
   */
  public EventMulticaster getEventMulticaster() {
    return eventMulticaster;
  }

  /**
   * <p>
   * Setter for the field <code>eventMulticaster</code>.
   * </p>
   * 
   * @param eventMulticaster a {@link org.beangle.commons.event.EventMulticaster} object.
   */
  public void setEventMulticaster(EventMulticaster eventMulticaster) {
    this.eventMulticaster = eventMulticaster;
  }

}
