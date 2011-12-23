/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import org.beangle.emsapp.security.Group;
import org.beangle.emsapp.security.nav.MenuProfile;
import org.beangle.emsapp.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;

public class MenuProfileAction extends SecurityActionSupport {

	protected void editSetting(Entity<?> entity) {
		OqlBuilder<Group> builder = OqlBuilder.from(Group.class, "g");
		builder.orderBy("g.code");
		put("groups", entityDao.search(builder));
	}

	@Override
	protected String getEntityName() {
		return MenuProfile.class.getName();
	}

}
