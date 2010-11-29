/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine;

public interface Task extends Runnable, Comparable<Task> {

	public int getPriority();

	public void increasePriority();

	public void before();

	public void after();

	public TaskStatus getStatus();
}
