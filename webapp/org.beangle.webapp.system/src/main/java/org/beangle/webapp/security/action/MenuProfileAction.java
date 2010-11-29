/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import org.beangle.model.Entity;
import org.beangle.security.blueprint.UserCategory;

public class MenuProfileAction extends SecurityActionSupport {

	protected void editSetting(Entity<?> entity) {
		put("categories", entityDao.getAll(UserCategory.class));
	}
}
