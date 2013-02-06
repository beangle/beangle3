/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.beangle.commons.notification.Message;
import org.beangle.commons.notification.MessageQueue;

public class DefaultMessageQueue<T extends Message> implements MessageQueue<T> {

  //FIXME buffer
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
