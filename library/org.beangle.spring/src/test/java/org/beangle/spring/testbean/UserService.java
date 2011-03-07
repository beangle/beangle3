/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.testbean;

import java.util.List;
import java.util.Map;

public class UserService {

	private UserProvider provider;

	private Map<String, Object> someMap;

	private List<Object> someList;

	public UserProvider getProvider() {
		return provider;
	}

	public void setProvider(UserProvider provider) {
		this.provider = provider;
	}

	public Map<String, Object> getSomeMap() {
		return someMap;
	}

	public void setSomeMap(Map<String, Object> someMap) {
		this.someMap = someMap;
	}

	public List<Object> getSomeList() {
		return someList;
	}

	public void setSomeList(List<Object> someList) {
		this.someList = someList;
	}

}
