/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.event;

/**
 * <p>
 * EventMulticaster interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EventMulticaster {
  /**
   * Add a listener to be notified of all events.
   * 
   * @param listener the listener to add
   */
  public void addListener(EventListener<?> listener);

  /**
   * Remove a listener from the notification list.
   * 
   * @param listener the listener to remove
   */
  public void removeListener(EventListener<?> listener);

  /**
   * Remove all listeners registered with this multicaster.
   * <p>
   * After a remove call, the multicaster will perform no action on event notification until new
   * listeners are being registered.
   */
  public void removeAllListeners();

  /**
   * <p>
   * multicast.
   * </p>
   * 
   * @param e a {@link org.beangle.commons.context.event.Event} object.
   */
  public void multicast(Event e);
}
