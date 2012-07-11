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

  @SuppressWarnings("rawtypes")
  private List<EventListener> listeners = CollectUtils.newArrayList();

  @SuppressWarnings("rawtypes")
  private Map<ListenerCacheKey, List<EventListener>> listenerCache = CollectUtils.newConcurrentHashMap();

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void multicast(Event e) {
    List<EventListener> adapted = getListeners(e);
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

  @SuppressWarnings("rawtypes")
  private List<EventListener> getListeners(Event e) {
    ListenerCacheKey key = new ListenerCacheKey(e.getClass(), e.getSource().getClass());
    List<EventListener> adapted = listenerCache.get(key);
    if (null == adapted) {
      synchronized (this) {
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
