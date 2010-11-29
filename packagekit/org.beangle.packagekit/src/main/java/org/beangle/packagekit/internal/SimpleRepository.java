/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.internal;

import org.beangle.packagekit.Registry;
import org.beangle.packagekit.Repository;
import org.beangle.packagekit.Resource;

/**
 * 仓库
 * 
 * @author chaostone
 */
public class SimpleRepository implements Repository {

	private String baseUrl;

	private String protocal;

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getLocation(Resource resource) {
		return baseUrl + "/" + resource.getId();
	}

	public Registry getRegistry() {
		return null;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

}
