/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.impl;

import org.beangle.model.persist.EntityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public abstract class BaseServiceImpl implements ApplicationEventPublisherAware {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected EntityDao entityDao;
	
	protected ApplicationEventPublisher eventPublisher;

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public void publish(ApplicationEvent event){
		eventPublisher.publishEvent(event);
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
}
