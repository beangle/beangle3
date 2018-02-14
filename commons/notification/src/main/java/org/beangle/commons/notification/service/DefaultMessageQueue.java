/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.notification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.beangle.commons.notification.Message;
import org.beangle.commons.notification.MessageQueue;

public class DefaultMessageQueue<T extends Message> implements MessageQueue<T> {

  private Queue<T> queue = new LinkedBlockingQueue<T>();

  public List<T> getMessages() {
    return new ArrayList<T>(queue);
  }

  public void addMessage(T message) {
    queue.add(message);
  }

  public void addMessages(List<T> contexts) {
    queue.addAll(contexts);
  }

  public T poll() {
    return (T) queue.poll();
  }

  public int size() {
    return queue.size();
  }

}
