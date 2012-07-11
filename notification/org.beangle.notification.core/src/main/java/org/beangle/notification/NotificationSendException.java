/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.LinkedHashMap;
import java.util.Map;

public class NotificationSendException extends NotificationException {

  private static final long serialVersionUID = -4019257253565582587L;

  private final Map<Object, Exception> failedMessages;

  public NotificationSendException(String msg, Throwable cause, Map<Object, Exception> failedMessages) {
    super(msg, cause);
    this.failedMessages = new LinkedHashMap<Object, Exception>(failedMessages);
  }

  public NotificationSendException(Map<Object, Exception> failedMessages) {
    this(null, null, failedMessages);
  }

  public Map<Object, Exception> getFailedMessages() {
    return failedMessages;
  }

}
