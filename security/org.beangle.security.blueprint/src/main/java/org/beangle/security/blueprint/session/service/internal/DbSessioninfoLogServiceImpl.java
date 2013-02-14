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
package org.beangle.security.blueprint.session.service.internal;

import java.util.List;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventListener;
import org.beangle.security.blueprint.session.model.SessioninfoBean;
import org.beangle.security.blueprint.session.model.SessioninfoLogBean;
import org.beangle.security.blueprint.session.service.SessioninfoLogService;
import org.beangle.security.core.session.LogoutEvent;
import org.beangle.security.core.session.Sessioninfo;

public class DbSessioninfoLogServiceImpl extends BaseServiceImpl implements SessioninfoLogService,
    EventListener<LogoutEvent> {

  @Override
  public void log(Sessioninfo info) {
    SessioninfoLogBean sessioninfoLog = new SessioninfoLogBean((SessioninfoBean) info);
    entityDao.save(sessioninfoLog);

  }

  @Override
  public List<SessioninfoLogBean> getLoggers(String username, int top) {
    OqlBuilder<SessioninfoLogBean> query = OqlBuilder.from(SessioninfoLogBean.class, "l");
    query.where("l.username = :username", username);
    query.orderBy("l.loginAt desc").limit(new PageLimit(1, top));
    return entityDao.search(query);
  }

  @Override
  public void onEvent(LogoutEvent event) {
    this.log(event.getSessioninfo());
  }

  @Override
  public boolean supportsEventType(Class<? extends Event> eventType) {
    return LogoutEvent.class.isAssignableFrom(eventType);
  }

  @Override
  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
