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
package org.beangle.security.core.session.impl;

import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.core.session.SessionStatusCache;

/**
 * Cache session status in local memory.
 * 
 * @author chaostone
 * @since 3.1.0
 */
public class LocalSessionStatusCache implements SessionStatusCache {

  private Map<String, SessionStatus> datas = CollectUtils.newConcurrentHashMap();

  @Override
  public SessionStatus get(String id) {
    return datas.get(id);
  }

  @Override
  public void put(String id, SessionStatus newstatus) {
    datas.put(id, newstatus);
  }

  @Override
  public void evict(String id) {
    datas.remove(id);
  }

  @Override
  public Set<String> getIds() {
    return datas.keySet();
  }

  
}
