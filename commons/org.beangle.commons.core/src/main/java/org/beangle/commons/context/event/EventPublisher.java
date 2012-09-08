/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.event;

/**
 * <p>
 * EventPublisher interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EventPublisher {

  /**
   * <p>
   * publishEvent.
   * </p>
   * 
   * @param event a {@link org.beangle.commons.context.event.Event} object.
   */
  void publishEvent(Event event);
}
