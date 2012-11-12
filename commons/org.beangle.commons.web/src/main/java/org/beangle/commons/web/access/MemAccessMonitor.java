/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
  private Map<String, AccessRequest> requests = CollectUtils.newConcurrentHashMap();

  public AccessRequest begin(HttpServletRequest request) {
    AccessRequest r = builder.build(request);
    if (null != r) requests.put(r.getSessionid(), r);
    return r;
  }

  public void end(AccessRequest request, HttpServletResponse response) {
    if (null == request) return;
    requests.remove(request.getSessionid());
    // need upgrade to serlvet 3.0
    // accessRequest.setStatus(response.getStatus());
    if (null != logger) {
      request.setEndAt(System.currentTimeMillis());
      logger.log(request);
    }
  }

  public List<AccessRequest> getRequests() {
    return CollectUtils.newArrayList(requests.values());
  }

  public void setLogger(AccessLogger logger) {
    this.logger = logger;
  }

  public void setBuilder(AccessRequestBuilder builder) {
    this.builder = builder;
  }

}
