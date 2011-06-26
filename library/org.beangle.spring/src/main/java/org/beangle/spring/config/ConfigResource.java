/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ConfigResource {

	private static Logger logger = LoggerFactory.getLogger(ConfigResource.class);

	private URL global;

	private List<URL> locals = CollectUtils.newArrayList();

	private URL user;

	public List<URL> getAllPaths() {
		List<URL> all = CollectUtils.newArrayList();
		if (null != global) {
			all.add(global);
		}
		if (null != locals) {
			all.addAll(locals);
		}
		if (null != user) {
			all.add(user);
		}
		return all;
	}

	public boolean isEmpty() {
		return null == global && null == user && (null == locals || locals.isEmpty());
	}

	public URL getGlobal() {
		return global;
	}

	public void setGlobal(URL first) {
		this.global = first;
	}

	public List<URL> getLocals() {
		return locals;
	}

	public void setLocals(List<URL> paths) {
		this.locals = paths;
	}

	public URL getUser() {
		return user;
	}

	public void setUser(URL last) {
		this.user = last;
	}

	public Resource[] getGlobals() {
		if (null == global) {
			return new Resource[0];
		} else {
			return new Resource[] { new UrlResource(global) };
		}
	}

	public Resource[] getUsers() {
		if (null == user) {
			return new Resource[0];
		} else {
			return new Resource[] { new UrlResource(user) };
		}
	}

	/**
	 * 提供0到多个的global配置方法
	 * 
	 * @param resources
	 */
	public void setGlobals(Resource[] resources) {
		if (null == resources || resources.length == 0) {
			global = null;
			return;
		}
		if (resources.length == 1) {
			try {
				global = resources[0].getURL();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.warn("there is {} resource,need only one", resources.length);
			int i = 0;
			for (Resource resource : resources) {
				logger.warn("resource {} is {}", i++, resource);
			}
		}
	}

	public void setUsers(Resource[] resources) {
		if (null == resources || resources.length == 0) {
			user = null;
			return;
		}
		if (resources.length == 1) {
			try {
				user = resources[0].getURL();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.warn("there is {} resource,need only one", resources.length);
			int i = 0;
			for (Resource resource : resources) {
				logger.warn("resource {} is {}", i++, resource);
			}
		}
	}

	public Resource[] getLocations() {
		Resource[] resources = new Resource[locals.size()];
		int i = 0;
		for (URL location : locals) {
			resources[i++] = new UrlResource(location);
		}
		return resources;
	}

	public void setLocations(Resource[] resources) {
		if (null != resources) {
			for (Resource r : resources) {
				try {
					locals.add(r.getURL());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			locals.clear();
		}
	}
}
