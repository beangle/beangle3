/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine;

import java.util.ArrayList;
import java.util.List;

import org.beangle.packagekit.Registry;
import org.beangle.packagekit.Resolver;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.engine.task.InstallTask;
import org.beangle.packagekit.engine.task.RemoveTask;
import org.beangle.packagekit.engine.task.TaskAssembler;
import org.beangle.packagekit.engine.task.UpdateTask;

public class Engine {

	private Registry registry;

	private Resolver resolver;

	private List<ResourceTask> resourceTasks = new ArrayList<ResourceTask>();

	public void addResource(Resource resource, UpdateAction action) {
		switch (action) {
		case INSTALL:
			resourceTasks.add(new InstallTask(resource));
			break;
		case UPDATE:
			Resource existed = registry.getResource(resource.getName());
			resourceTasks.add(new UpdateTask(resource, existed));
			break;
		case REMOVE:
			resourceTasks.add(new RemoveTask(resource));
		}
	}

	public List<Task> genTasks() {
		TaskAssembler assembler = new TaskAssembler(resolver, registry);
		return assembler.assemble(resourceTasks);
	}

	public void run() {
		new Transaction(genTasks()).run();
	}

	public void setResolver(Resolver resolver) {
		this.resolver = resolver;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

}
