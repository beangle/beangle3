/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

public class NotificationException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NotificationException(Throwable cause) {
    super(cause);
  }

  public NotificationException(String message) {
    super(message);
  }

  public NotificationException(String message, Throwable cause) {
    super(message, cause);
  }
}
