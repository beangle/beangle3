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

public class DefaultMessageQueue<T extends Message> implements MessageQueue<T> {

	private Buffer contextBuffer = BufferUtils.synchronizedBuffer(new UnboundedFifoBuffer());

	@SuppressWarnings("unchecked")
	public List<T> getMessages() {
		return new ArrayList<T>(contextBuffer);
	}

	@SuppressWarnings("unchecked")
	public void addMessage(T message) {
		contextBuffer.add(message);
	}

	@SuppressWarnings("unchecked")
	public void addMessages(List<T> contexts) {
		contextBuffer.addAll(contexts);
	}

	@SuppressWarnings("unchecked")
	public T remove() {
		return (T) contextBuffer.remove();
	}

	public int size() {
		return contextBuffer.size();
	}

}
