/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine.task;

import org.beangle.packagekit.Repository;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.engine.ResourceTask;
import org.beangle.packagekit.engine.Task;
import org.beangle.packagekit.engine.TaskStatus;
import org.beangle.packagekit.engine.UpdateAction;
import org.beangle.packagekit.wagon.Wagon;
import org.beangle.packagekit.wagon.WagonFactory;

public abstract class AbstractResourceTask implements ResourceTask {

	private int priority;

	protected Resource resource;

	protected String cacheDir;

	protected TaskStatus status = TaskStatus.WAIT;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void increasePriority() {
		priority++;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public int compareTo(Task o) {
		return o.getPriority() - priority;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void after() {
		status = TaskStatus.END;
	}

	public void before() {
		status = TaskStatus.START;
		if (getAction() == UpdateAction.INSTALL || getAction() == UpdateAction.UPDATE) {
			Repository repository = resource.getRepository();
			if (null != repository) {
				Wagon wagon = WagonFactory.getWagon(repository.getProtocal());
				wagon.transfer(repository.getLocation(resource),
						cacheDir + "/" + resource.getPackageName());
			}
		}
	}

}
