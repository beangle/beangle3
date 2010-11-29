/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	List<Task> tasks = new ArrayList<Task>();

	public void run() {
		for (Task task : tasks) {
			task.before();
		}

		for (Task task : tasks) {
			task.run();
		}

		for (Task task : tasks) {
			task.after();
		}
	}

	public Transaction(List<Task> tasks) {
		super();
		this.tasks = tasks;
	}

}
