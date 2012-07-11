/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.impl;

import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventMulticaster;
import org.beangle.commons.dao.EntityDao;
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
   * @param e a {@link org.beangle.commons.context.event.Event} object.
   */
  public void publish(Event e) {
    if (null != eventMulticaster) eventMulticaster.multicast(e);
  }

  /**
   * <p>
   * Getter for the field <code>eventMulticaster</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.context.event.EventMulticaster} object.
   */
  public EventMulticaster getEventMulticaster() {
    return eventMulticaster;
  }

  /**
   * <p>
   * Setter for the field <code>eventMulticaster</code>.
   * </p>
   * 
   * @param eventMulticaster a {@link org.beangle.commons.context.event.EventMulticaster} object.
   */
  public void setEventMulticaster(EventMulticaster eventMulticaster) {
    this.eventMulticaster = eventMulticaster;
  }

}
