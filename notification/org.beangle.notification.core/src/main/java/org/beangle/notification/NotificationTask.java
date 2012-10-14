/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

/**
 * 消息通知任务
 */
public interface NotificationTask<T extends Message> {

  Notifier<T> getNotifier();

  void setNotifier(Notifier<T> notifier);

  MessageQueue<T> getMessageQueue();

  void setMessageQueue(MessageQueue<T> messageQueue);

  void send();
}
