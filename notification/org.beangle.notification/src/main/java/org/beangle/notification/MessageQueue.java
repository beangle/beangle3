/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.List;

//$Id:MessageQueue.java Mar 22, 2009 9:04:20 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */

/**
 * 消息队列
 */
public interface MessageQueue {

	public int size();

	public Message remove();

	public List<Message> getMessages();

	public void addMessages(List<Message> contexts);

	public void addMessage(Message message);

}
