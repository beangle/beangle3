/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.lang;

import java.util.List;

import org.beangle.collection.CollectUtils;

/**
 * 
 * @author chaostone
 * @since 2.2.2
 */
public class Tasks {

	private List<Thread> tasks = CollectUtils.newArrayList();

	public Tasks() {
		super();
	}

	public Tasks(Runnable... runnables) {
		for (Runnable one : runnables) {
			addTask(one);
		}
	}

	public void addTask(Runnable runnable) {
		tasks.add(new Thread(runnable));
	}

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
