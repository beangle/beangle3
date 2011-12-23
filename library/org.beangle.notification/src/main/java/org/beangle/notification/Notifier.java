/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

public interface Notifier<T extends Message> {

	String getType();

	void deliver(T message) throws NotificationException;
}
