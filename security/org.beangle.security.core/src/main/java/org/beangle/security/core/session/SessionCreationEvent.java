/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.commons.context.event.Event;

/**
 * Generic session creation event which indicates that a session (potentially
 * represented by a security context) has begun.
 * 
 * @author chaostone
 */
public abstract class SessionCreationEvent extends Event {

  private static final long serialVersionUID = 1L;

  public SessionCreationEvent(Object source) {
    super(source);
  }
}
