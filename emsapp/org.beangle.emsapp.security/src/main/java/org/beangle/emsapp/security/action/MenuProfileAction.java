/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;

public class MenuProfileAction extends SecurityActionSupport {

	protected void editSetting(Entity<?> entity) {
		put("groups", entityDao.getAll(Group.class));
	}

	@Override
	protected String getEntityName() {
		return MenuProfile.class.getName();
	}

}
