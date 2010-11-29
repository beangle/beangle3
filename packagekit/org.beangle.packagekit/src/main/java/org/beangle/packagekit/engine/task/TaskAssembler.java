/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.packagekit.Registry;
import org.beangle.packagekit.Resolver;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.engine.ResourceTask;
import org.beangle.packagekit.engine.Task;

public class TaskAssembler {

	private final Resolver resolver;

	private final Registry registry;

	private Map<String, ResourceTask> resolved = new HashMap<String, ResourceTask>();

	public TaskAssembler(Resolver resolver, Registry registry) {
		super();
		this.resolver = resolver;
		this.registry = registry;
	}

	public List<Task> assemble(List<ResourceTask> tasks) {
		for (ResourceTask task : tasks) {
			assemble(task);
		}
		List<Task> newTasks = new ArrayList<Task>(resolved.values());
		Collections.sort(newTasks);
		return newTasks;
	}

	private void assemble(ResourceTask task) {
		Resource resource = task.getResource();
		// 避免递归循环
		if (resolved.containsKey(resource.getName())) { return; }
		resolved.put(resource.getName(), task);
		switch (task.getAction()) {
		case INSTALL:
		case UPDATE:
			assembeUpdate(resource);
			break;
		case REMOVE:
			assembeRemove(resource);
			break;
		}
	}

	private void assembeUpdate(Resource resource) {
		List<Resource> requires = resolver.getRequires(resource);
		for (Resource require : requires) {
			ResourceTask resolve = resolved.get(require.getName());
			if (null == resolve) {
				Resource existed = getExisted(require.getName());
				ResourceTask newTask = null;
				if (null == existed) {
					newTask = new InstallTask(require);
				} else if (require.isNewerThen(existed)) {
					newTask = new UpdateTask(require, existed);
				}
				if (null != newTask) {
					newTask.increasePriority();
					assemble(newTask);
				}
			} else {
				resolve.increasePriority();
				if (require.isNewerThen(resolve.getResource())) {
					resolve.setResource(require);
				}
			}
		}
	}

	private void assembeRemove(Resource resource) {
		List<Resource> dependencies = resolver.getDependencies(resource);
		for (Resource depend : dependencies) {
			ResourceTask resolve = resolved.get(depend.getName());
			if (null == resolve) {
				Resource existed = getExisted(depend.getName());
				if (null != existed) {
					ResourceTask newTask = new RemoveTask(existed);
					newTask.increasePriority();
					assemble(newTask);
				}
			} else {
				resolve.increasePriority();
			}
		}
	}

	private Resource getExisted(String name) {
		return registry.getResource(name);
	}

}
