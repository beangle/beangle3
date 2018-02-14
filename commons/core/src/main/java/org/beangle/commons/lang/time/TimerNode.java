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
package org.beangle.commons.lang.time;

import java.util.ArrayList;
import java.util.List;

/**
 * Timer Node in stack
 *
 * @author chaostone
 * @since 3.0.0
 */
public class TimerNode implements java.io.Serializable {

  private static final long serialVersionUID = -6180672043920208784L;

  List<TimerNode> children = new ArrayList<TimerNode>();

  String resource;

  long startTime;

  long totalTime;

  public TimerNode(String resource, long startTime) {
    this.resource = resource;
    this.startTime = startTime;
  }

  public void start(long startTime) {
    this.startTime = startTime;
  }

  public long end() {
    this.totalTime = System.currentTimeMillis() - startTime;
    return this.totalTime;
  }

  public String getResource() {
    return resource;
  }

  /**
   * Get a formatted string representing all the methods that took longer than a specified time.
   */
  public String getPrintable() {
    return getPrintable("");
  }

  protected String getPrintable(String indent) {
    StringBuilder buffer = new StringBuilder();
    buffer.append(indent);
    buffer.append("[" + totalTime + "ms] - " + resource);

    for (TimerNode child : children) {
      buffer.append('\n').append(child.getPrintable(indent + "  "));
    }
    return buffer.toString();
  }

}
