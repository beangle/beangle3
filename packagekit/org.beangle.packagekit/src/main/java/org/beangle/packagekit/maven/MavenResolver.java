/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.maven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beangle.packagekit.Resolver;
import org.beangle.packagekit.Resource;

public class MavenResolver implements Resolver {

	public List<Resource> getRequires(Resource resource) {
		if (resource.getId().startsWith("beangle-packagekit")) {
			List<Resource> requires = new ArrayList<Resource>();
			requires.add(MavenUtils.build("beangle-commons-1.0.0"));
			if (resource.getId().startsWith("beangle-packagekit-webapp")) {
				requires.add(MavenUtils.build("beangle-packagekit-1.0.0"));
			}
			return requires;
		} else {
			return Collections.emptyList();
		}
	}

	public List<Resource> getDependencies(Resource resource) {
		if (resource.getId().startsWith("beangle-commons")) {
			List<Resource> requires = new ArrayList<Resource>();
			requires.add(MavenUtils.build("beangle-packagekit-1.0.0"));
			requires.add(MavenUtils.build("beangle-packagekit-webapp-1.0.0"));
			return requires;
		} else if (resource.getId().startsWith("beangle-packagekit-1")) {
			List<Resource> requires = new ArrayList<Resource>();
			requires.add(MavenUtils.build("beangle-packagekit-webapp-1.0.0"));
			return requires;
		} else {
			return Collections.emptyList();
		}
	}

}
