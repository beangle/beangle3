/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @since 3.0.0
 */
public final class TimerTrace {

  private static final Logger logger = LoggerFactory.getLogger(TimerTrace.class);

  // A reference to the current TimerNode
  protected static ThreadLocal<TimerStack> curStack = new ThreadLocal<TimerStack>();

  /**
   * System property that controls whether this timer should be used or not. Set to "true" activates
   * the timer. Set to "false" to disactivate.
   */
  public static final String ACTIVATE_PROPERTY = "beangle.profile.activate";

  /**
   * System property that controls the min time, that if exceeded will cause a log (at INFO level)
   * to be created.
   */
  public static final String MIN_TIME = "beangle.profile.mintime";

  /**
   * Initialized in a static block, it can be changed at runtime by calling setActive(...)
   */
  private static boolean active;

  /**
   * Get the min time for this profiling, it searches for a System property
   * 'beangle.profile.mintime' and default to 0.
   */
  private static int mintime;

  static {
    active = "true".equalsIgnoreCase(System.getProperty(ACTIVATE_PROPERTY));
    try {
      mintime = Integer.parseInt(System.getProperty(MIN_TIME, "0"));
    } catch (NumberFormatException e) {
    }
  }

  /**
   * Create and start a performance profiling with the <code>name</code> given. Deal with
   * profile hierarchy automatically, so caller don't have to be concern about it.
   * 
   * @param name profile name
   */
  public static void start(String name) {
    if (!active) return;

    TimerNode root = new TimerNode(name, System.currentTimeMillis());
    TimerStack stack = (TimerStack) curStack.get();
    if (null == stack) curStack.set(new TimerStack(root));
    else stack.push(root);
  }

  /**
   * End a preformance profiling with the <code>name</code> given. Deal with
   * profile hierarchy automatically, so caller don't have to be concern about it.
   * 
   * @param name profile name
   */
  public static void end() {
    if (!active) return;

    TimerStack stack = curStack.get();
    if (null == stack) return;

    TimerNode currentNode = stack.pop();

    if (currentNode != null) {
      TimerNode parent = stack.peek();
      long total = currentNode.end();
      // if we are the root timer, then print out the times
      if (parent == null) {
        printTimes(currentNode);
        curStack.set(null); // for those servers that use thread pooling
      } else {
        if (total > mintime) parent.children.add(currentNode);
      }
    }
  }

  public void clear() {
    curStack.set(null);
  }

  /**
   * Do a log (at INFO level) of the time taken for this particular profiling.
   * 
   * @param currentTimer profiling timer bean
   */
  private static void printTimes(TimerNode currentTimer) {
    if (logger.isInfoEnabled()) logger.info(currentTimer.getPrintable());

  }

  /**
   * Get the min time for this profiling, it searches for a System property
   * 'beangle.profile.mintime' and default to 0.
   * 
   * @return long
   */
  public static int getMinTime() {
    return mintime;
  }

  /**
   * Change mintime
   * 
   * @param mintime
   */
  public static void setMinTime(int mintime) {
    System.setProperty(MIN_TIME, String.valueOf(mintime));
    TimerTrace.mintime = mintime;
  }

  /**
   * Determine if profiling is being activated, by searching for a system property
   * 'beangle.profile.activate', default to false (profiling is off).
   * 
   * @return <tt>true</tt>, if active, <tt>false</tt> otherwise.
   */
  public static boolean isActive() {
    return active;
  }

  /**
   * Turn profiling on or off.
   * 
   * @param active
   */
  public static void setActive(boolean active) {
    if (active) System.setProperty(ACTIVATE_PROPERTY, "true");
    else System.clearProperty(ACTIVATE_PROPERTY);
    TimerTrace.active = active;
  }

}
