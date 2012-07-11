/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * Tasks class.
 * </p>
 * 
 * @author chaostone
 * @since 2.2.2
 * @version $Id: $
 */
public class Tasks {

  private List<Thread> tasks = CollectUtils.newArrayList();

  /**
   * <p>
   * Constructor for Tasks.
   * </p>
   */
  public Tasks() {
    super();
  }

  /**
   * <p>
   * Constructor for Tasks.
   * </p>
   * 
   * @param runnables a {@link java.lang.Runnable} object.
   */
  public Tasks(Runnable... runnables) {
    for (Runnable one : runnables) {
      addTask(one);
    }
  }

  /**
   * <p>
   * addTask.
   * </p>
   * 
   * @param runnable a {@link java.lang.Runnable} object.
   */
  public void addTask(Runnable runnable) {
    tasks.add(new Thread(runnable));
  }

  /**
   * <p>
   * run.
   * </p>
   */
  public void run() {
    for (Thread task : tasks)
      task.start();
    for (Thread task : tasks) {
      try {
        task.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
