/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
