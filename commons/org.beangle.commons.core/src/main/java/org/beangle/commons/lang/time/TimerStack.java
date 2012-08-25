/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
