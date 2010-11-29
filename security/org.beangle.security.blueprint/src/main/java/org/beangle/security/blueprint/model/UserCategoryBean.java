/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.UserCategory;

public class UserCategoryBean extends LongIdObject implements UserCategory {

	private static final long serialVersionUID = -5929038500510261629L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserCategoryBean() {
		super();
	}

	public UserCategoryBean(Long id) {
		super();
		this.setId(id);
	}

	@Override
	public String toString() {
		return name;
	}

	
}
