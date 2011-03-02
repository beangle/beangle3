/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import org.beangle.notification.Message;
import org.beangle.notification.MessageQueue;
import org.beangle.notification.NotificationException;
import org.beangle.notification.NotificationTask;
import org.beangle.notification.Notifier;
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
		while (queue.size() > 0) {
			T msg = queue.remove();
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
		}
	}

	public void setObserver(SendingObserver observer) {
		this.observer = observer;
	}

}
