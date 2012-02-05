package org.beangle.context.spring;

import java.io.IOException;
import java.net.URL;

import org.beangle.context.inject.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class SpringConfigResource extends ConfigResource {

	private static Logger logger = LoggerFactory.getLogger(SpringConfigResource.class);
	
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
