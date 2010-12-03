/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.internal;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.packagekit.Registry;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.maven.MavenUtils;

public class SimpleRegistry implements Registry {

	final Map<String, Resource> resourceMap = new HashMap<String, Resource>();

	public SimpleRegistry() {
		super();
		load(null);
	}

	public List<Resource> getResources() {
		return new ArrayList<Resource>(resourceMap.values());
	}

	public void load(URL url) {
		resourceMap.put("beangle-commons", MavenUtils.build("beangle-commons-1.0.0"));
		resourceMap.put("beangle-packagekit", MavenUtils.build("beangle-packagekit-1.0.0"));
		resourceMap.put("beangle-packagekit-webapp",
				MavenUtils.build("beangle-packagekit-webapp-1.0.0"));
	}

	public Resource getResource(String name) {
		return resourceMap.get(name);
	}

	public List<Resource> getUpdates(Registry newer) {
		List<Resource> newResources = new ArrayList<Resource>();
		for (String name : resourceMap.keySet()) {
			Resource newResource = newer.getResource(name);
			if (null != newResource && newResource.isNewerThen(resourceMap.get(name))) {
				newResources.add(resourceMap.get(name));
			}
		}
		return newResources;
	}

}
