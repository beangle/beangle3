/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.beangle.notification.Message;
import org.beangle.notification.MessageQueue;

//$Id:DefaultMessageQueue.java Mar 22, 2009 9:08:49 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultMessageQueue implements MessageQueue {

	private Buffer contextBuffer = BufferUtils.synchronizedBuffer(new UnboundedFifoBuffer());;

	@SuppressWarnings("unchecked")
	public List<Message> getMessages() {
		return new ArrayList<Message>(contextBuffer);
	}

	@SuppressWarnings("unchecked")
	public void addMessage(Message message) {
		contextBuffer.add(message);
	}

	@SuppressWarnings("unchecked")
	public void addMessages(List<Message> contexts) {
		contextBuffer.addAll(contexts);
	}

	public Message remove() {
		return (Message) contextBuffer.remove();
	}

	public int size() {
		return contextBuffer.size();
	}

}
