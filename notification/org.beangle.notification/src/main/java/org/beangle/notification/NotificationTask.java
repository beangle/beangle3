/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import org.beangle.notification.Notifier;

//$Id:NotificationTask.java Mar 22, 2009 8:26:06 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
/**
 * 消息通知任务
 */
public interface NotificationTask {

	Notifier getNotifier();

	void setNotifier(Notifier notifier);

	MessageQueue getMessageQueue();

	void setMessageQueue(MessageQueue messageQueue);

	void send();
}
