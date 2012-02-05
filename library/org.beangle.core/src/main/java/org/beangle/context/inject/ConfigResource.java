/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.inject;

import java.net.URL;
import java.util.List;

import org.beangle.collection.CollectUtils;

public class ConfigResource {

	protected URL global;

	protected List<URL> locals = CollectUtils.newArrayList();

	protected URL user;

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
}
