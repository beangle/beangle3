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
package org.beangle.ems.log.service;

import static org.beangle.commons.bean.PropertyUtils.getProperty;

import java.util.Date;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.event.BusinessEvent;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventListener;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.ems.log.model.BusinessLogDetailBean;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 A M chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements EventListener<Event> {

  public void onEvent(Event event) {
    Session session = SecurityContext.getSession();
    if (null == session) return;
    BusinessLogBean log = new BusinessLogBean();
    log.setOperateAt(new Date(event.getTimestamp()));
    log.setOperation(Strings.defaultIfBlank(event.getSubject(), "  "));
    log.setResource(Strings.defaultIfBlank(event.getResource(), "  "));
    log.setOperator(session.getPrincipal().getName());
    Session.Agent agent = session.getAgent();
    if (null != agent) {
      log.setIp(agent.getIp());
      log.setAgent(agent.getOs() + " " + agent.getName());
      // log.setEntry(Strings.defaultIfBlank((String) getProperty(details, "lastAccessUri"), "--"));
    }
    if (null != event.getDetail()) {
      log.setDetail(new BusinessLogDetailBean(log, event.getDetail()));
    }
    entityDao.saveOrUpdate(log);
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return BusinessEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
