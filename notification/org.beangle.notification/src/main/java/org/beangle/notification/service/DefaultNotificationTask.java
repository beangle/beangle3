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

//$Id:DefaultNotificationTask.java Mar 22, 2009 8:41:54 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultNotificationTask implements NotificationTask {

	protected static final Logger logger = LoggerFactory.getLogger(DefaultNotificationTask.class);

	private MessageQueue queue;

	private Notifier notifier;

	private long taskInterval = 0;

	public MessageQueue getMessageQueue() {
		return queue;
	}

	public void setMessageQueue(MessageQueue messageQueue) {
		this.queue = messageQueue;
	}

	public Notifier getNotifier() {
		return notifier;
	}

	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}

	public void send() {
		while (queue.size() > 0) {
			Message context = (Message) queue.remove();
			try {
				notifier.sendMessage(context);
				if (taskInterval > 0) {
					Thread.sleep(taskInterval);
				}
			} catch (NotificationException e) {
				logger.error("send error", e);
			} catch (InterruptedException e) {
				logger.error("send error", e);
			}
		}
	}

}
