/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.notification.service;

import org.beangle.commons.notification.Message;
import org.beangle.commons.notification.MessageQueue;
import org.beangle.commons.notification.NotificationException;
import org.beangle.commons.notification.NotificationTask;
import org.beangle.commons.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNotificationTask<T extends Message> implements NotificationTask<T> {

  protected static final Logger logger = LoggerFactory.getLogger(DefaultNotificationTask.class);

  private MessageQueue<T> queue;

  private Notifier<T> notifier;

  private SendingObserver observer;

  private long taskInterval = 0;

  public DefaultNotificationTask() {
    super();
    queue = new DefaultMessageQueue<T>();
  }

  public MessageQueue<T> getMessageQueue() {
    return queue;
  }

  public void setMessageQueue(MessageQueue<T> messageQueue) {
    this.queue = messageQueue;
  }

  public Notifier<T> getNotifier() {
    return notifier;
  }

  public void setNotifier(Notifier<T> notifier) {
    this.notifier = notifier;
  }

  public void send() {
    T msg = queue.poll();
    while (null != msg) {
      try {
        if (null != observer) observer.onStart(msg);
        notifier.deliver(msg);
        if (taskInterval > 0) {
          Thread.sleep(taskInterval);
        }
      } catch (NotificationException e) {
        logger.error("send error", e);
      } catch (InterruptedException e) {
        logger.error("send error", e);
      }
      if (null != observer) observer.onFinish(msg);
      msg = queue.poll();
    }
  }

  public void setObserver(SendingObserver observer) {
    this.observer = observer;
  }

}
