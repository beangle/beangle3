/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;

public class CachedResourceAccessor extends DefaultResourceAccessor implements Initializing {

  private List<AccessLog> accessLogs = CollectUtils.newArrayList();

  // 3000 milliseconds
  private long minDuration = 3000;

  // default 500
  private int cacheSize = 500;

  public void init() throws Exception {
    Assert.isTrue(minDuration > 0, "minDuration must greater then 0");
    Assert.isTrue(cacheSize > 100, "cacheSize should greate then 100");
  }

  public AccessLog beginAccess(HttpServletRequest request, Date date) {
    AccessLog accessLog = buildLog(request);
    accessLog.setBeginAt(date);
    synchronized (accessLogs) {
      accessLogs.add(accessLog);
      if (accessLogs.size() >= cacheSize) {
        shrink(accessLogs);
        if (accessLogs.size() >= cacheSize) {
          flush(accessLogs);
        }
      }
    }
    return accessLog;
  }

  public void endAccess(AccessLog log, Date date) {
    log.setEndAt(date);
  }

  public void finish() {
    if (!accessLogs.isEmpty()) {
      flush(accessLogs);
    }
  }

  public void start() {

  }

  public List<AccessLog> getAccessLogs() {
    return accessLogs;
  }

  protected void shrink(List<AccessLog> accessLogs) {
    for (Iterator<AccessLog> iterator = accessLogs.iterator(); iterator.hasNext();) {
      AccessLog accessLog = (AccessLog) iterator.next();
      if (accessLog.getDuration() < minDuration) {
        iterator.remove();
      }
    }
  }

  protected void flush(List<AccessLog> accessLogs) {
    for (final AccessLog accessLog : accessLogs) {
      logger.info(accessLog.toString());
    }
    accessLogs.clear();
  }

  public Long getMinDuration() {
    return minDuration;
  }

  public void setMinDuration(Long minDuration) {
    this.minDuration = minDuration;
  }

  public Integer getCacheSize() {
    return cacheSize;
  }

  public void setCacheSize(Integer cacheSize) {
    this.cacheSize = cacheSize;
  }
}
