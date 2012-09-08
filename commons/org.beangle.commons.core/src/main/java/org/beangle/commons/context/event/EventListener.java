/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.event;

/**
 * <p>
 * EventListener interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EventListener<E extends Event> extends java.util.EventListener {
  /**
   * Handle an application event.
   * 
   * @param event the event to respond to
   * @param <E> a E object.
   */
  public void onEvent(E event);

  /**
   * Determine whether this listener actually supports the given event type.
   * 
   * @param eventType a {@link java.lang.Class} object.
   * @return a boolean.
   */
  public boolean supportsEventType(Class<? extends Event> eventType);

  /**
   * Determine whether this listener actually supports the given source type.
   * 
   * @param sourceType a {@link java.lang.Class} object.
   * @return a boolean.
   */
  boolean supportsSourceType(Class<?> sourceType);
}
