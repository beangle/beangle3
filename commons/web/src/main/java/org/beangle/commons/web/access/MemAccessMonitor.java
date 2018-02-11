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
package org.beangle.commons.web.access;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventListener;
import org.beangle.commons.web.session.HttpSessionDestroyedEvent;

/**
 * Memory access monitor.
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class MemAccessMonitor implements AccessMonitor, EventListener<HttpSessionDestroyedEvent> {

  private AccessLogger logger;
  private AccessRequestBuilder builder;
  // sessionid-->request quene
  private Map<String, List<AccessRequest>> requests = CollectUtils.newConcurrentHashMap();

  public AccessRequest begin(HttpServletRequest request) {
    AccessRequest r = builder.build(request);
    if (null != r) {
      List<AccessRequest> quene = requests.get(r.getSessionid());
      if (null == quene) {
        quene = Collections.synchronizedList(new LinkedList<AccessRequest>());
        requests.put(r.getSessionid(), quene);
      }
      quene.add(r);
    }
    return r;
  }

  public void end(AccessRequest request, HttpServletResponse response) {
    if (null == request) return;
    List<AccessRequest> quene = requests.get(request.getSessionid());
    if (null != quene) quene.remove(request);
    request.setStatus(response.getStatus());
    if (null != logger) {
      request.setEndAt(System.currentTimeMillis());
      logger.log(request);
    }
  }

  public List<AccessRequest> getRequests() {
    List<AccessRequest> result = CollectUtils.newArrayList(requests.size());
    for (List<AccessRequest> quene : requests.values()) {
      result.addAll(quene);
    }
    return result;
  }

  public void setLogger(AccessLogger logger) {
    this.logger = logger;
  }

  public void setBuilder(AccessRequestBuilder builder) {
    this.builder = builder;
  }

  public void onEvent(HttpSessionDestroyedEvent event) {
    requests.remove(event.getSession().getId());
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return HttpSessionDestroyedEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
