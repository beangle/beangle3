/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.impl;

import org.beangle.context.event.Event;
import org.beangle.context.event.EventMulticaster;
import org.beangle.model.persist.EntityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected EntityDao entityDao;

	protected EventMulticaster eventMulticaster;

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public void publish(Event e) {
		if (null != eventMulticaster) eventMulticaster.multicast(e);
	}

	public EventMulticaster getEventMulticaster() {
		return eventMulticaster;
	}

	public void setEventMulticaster(EventMulticaster eventMulticaster) {
		this.eventMulticaster = eventMulticaster;
	}

}
