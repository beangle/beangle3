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
package org.beangle.commons.context.event;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * DefaultEventMulticaster class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultEventMulticaster implements EventMulticaster {

  protected List<EventListener<?>> listeners = CollectUtils.newArrayList();

  private Map<ListenerCacheKey, List<EventListener<?>>> listenerCache = CollectUtils.newConcurrentHashMap();

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void multicast(Event e) {
    List<EventListener<?>> adapted = getListeners(e);
    for (EventListener listener : adapted)
      listener.onEvent(e);
  }

  /** {@inheritDoc} */
  public void addListener(EventListener<?> listener) {
    synchronized (this) {
      listeners.add(listener);
      listenerCache.clear();
    }
  }

  /** {@inheritDoc} */
  public void removeListener(EventListener<?> listener) {
    synchronized (this) {
      listeners.remove(listener);
      listenerCache.clear();
    }
  }

  /**
   * <p>
   * removeAllListeners.
   * </p>
   */
  public void removeAllListeners() {
    synchronized (this) {
      listeners.clear();
      listenerCache.clear();
    }
  }

  protected void initListeners() {

  }

  private List<EventListener<?>> getListeners(Event e) {
    initListeners();
    ListenerCacheKey key = new ListenerCacheKey(e.getClass(), e.getSource().getClass());
    List<EventListener<?>> adapted = listenerCache.get(key);
    if (null == adapted) {
      synchronized (this) {
        if (null == adapted) {
          adapted = CollectUtils.newArrayList();
          for (EventListener<?> listener : listeners) {
            if (listener.supportsEventType(e.getClass())
                && listener.supportsSourceType(e.getSource().getClass())) {
              adapted.add(listener);
            }
          }
          listenerCache.put(key, adapted);
        }
      }
    }
    return adapted;
  }

  private static class ListenerCacheKey {

    private final Class<?> eventType;

    private final Class<?> sourceType;

    public ListenerCacheKey(Class<?> eventType, Class<?> sourceType) {
      this.eventType = eventType;
      this.sourceType = sourceType;
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) { return true; }
      ListenerCacheKey otherKey = (ListenerCacheKey) other;
      return (this.eventType.equals(otherKey.eventType) && this.sourceType.equals(otherKey.sourceType));
    }

    @Override
    public int hashCode() {
      return this.eventType.hashCode() * 29 + this.sourceType.hashCode();
    }
  }
}
