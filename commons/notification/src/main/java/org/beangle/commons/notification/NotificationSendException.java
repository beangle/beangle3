/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.notification;

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
