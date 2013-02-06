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
package org.beangle.commons.event;

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
  void addListener(EventListener<?> listener);

  /**
   * Remove a listener from the notification list.
   * 
   * @param listener the listener to remove
   */
  void removeListener(EventListener<?> listener);

  /**
   * Remove all listeners registered with this multicaster.
   * <p>
   * After a remove call, the multicaster will perform no action on event notification until new
   * listeners are being registered.
   */
  void removeAllListeners();

  /**
   * <p>
   * multicast.
   * </p>
   * 
   * @param e a {@link org.beangle.commons.event.Event} object.
   */
  void multicast(Event e);
}
