/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification;

import java.util.List;

/**
 * 消息队列
 */
public interface MessageQueue<T extends Message> {

	public int size();

	public T remove();

	public List<T> getMessages();

	public void addMessages(List<T> contexts);

	public void addMessage(T message);

}
