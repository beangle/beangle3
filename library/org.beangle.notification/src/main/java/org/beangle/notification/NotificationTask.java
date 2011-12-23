/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import org.beangle.notification.Notifier;

/**
 * 消息通知任务
 */
public interface NotificationTask<T extends Message> {

	public Notifier<T> getNotifier();

	public void setNotifier(Notifier<T> notifier);

	public MessageQueue<T> getMessageQueue();

	public void setMessageQueue(MessageQueue<T> messageQueue);

	public void send();
}
