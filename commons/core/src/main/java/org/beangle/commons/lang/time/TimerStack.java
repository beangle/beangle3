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
package org.beangle.commons.lang.time;

import java.util.Arrays;

/**
 * Record timer nodes
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class TimerStack {

  int index = -1;

  TimerNode[] nodes;

  public TimerStack(TimerNode root, int initCapacity) {
    super();
    nodes = new TimerNode[initCapacity];
    push(root);
  }

  public TimerStack(TimerNode root) {
    this(root, 15);
  }

  private void ensureCapacity() {
    if (index >= nodes.length) {
      int newCapacity = nodes.length * 2;
      nodes = Arrays.copyOf(nodes, newCapacity);
    }
  }

  public void push(TimerNode node) {
    ensureCapacity();
    nodes[++index] = node;
  }

  public TimerNode pop() {
    if (index < 0) return null;
    TimerNode top = nodes[index];
    nodes[index] = null;
    index--;
    return top;
  }

  public TimerNode peek() {
    if (index < 0) return null;
    return nodes[index];
  }

}
