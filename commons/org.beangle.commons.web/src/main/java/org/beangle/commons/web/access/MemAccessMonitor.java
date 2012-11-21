/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.web.access;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;

/**
 * Memory access monitor.
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class MemAccessMonitor implements AccessMonitor {

  private AccessLogger logger;
  private AccessRequestBuilder builder;
  private Map<String, List<AccessRequest>> requests = CollectUtils.newConcurrentHashMap();

  public AccessRequest begin(HttpServletRequest request) {
    AccessRequest r = builder.build(request);
    if (null != r) {
      List<AccessRequest> quene = requests.get(r.getSessionid());
      if (null == quene) {
        quene = CollectUtils.newArrayList(1);
        requests.put(r.getSessionid(), quene);
      }
      quene.add(r);
    }
    return r;
  }

  public void end(AccessRequest request, HttpServletResponse response) {
    if (null == request) return;
    List<AccessRequest> quene = requests.get(request.getSessionid());
    quene.remove(request);
    // need upgrade to serlvet 3.0
    // accessRequest.setStatus(response.getStatus());
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

}
