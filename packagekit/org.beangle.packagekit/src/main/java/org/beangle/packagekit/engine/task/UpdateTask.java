/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine.task;

import org.beangle.packagekit.Resource;
import org.beangle.packagekit.engine.ResourceTask;
import org.beangle.packagekit.engine.UpdateAction;

public class UpdateTask extends AbstractResourceTask implements ResourceTask {

	public Resource older;

	public UpdateTask() {
		super();
	}

	public UpdateTask(Resource newer, Resource older) {
		super();
		this.resource = newer;
		this.older = older;
	}

	public void run() {
		System.out.println("update:" + older.getId() + " to " + resource.getId());
	}

	public UpdateAction getAction() {
		return UpdateAction.INSTALL;
	}
}
