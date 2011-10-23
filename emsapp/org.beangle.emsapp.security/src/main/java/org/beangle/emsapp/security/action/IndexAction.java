/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.profile.GroupPropertyMeta;
import org.beangle.ems.security.profile.UserPropertyMeta;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;

public class IndexAction extends SecurityActionSupport {

	public String stat() {
		populateUserStat();
		// state menus
		List<MenuProfile> menuProfiles = entityDao.getAll(MenuProfile.class);
		Map<Long, List<?>> menuStats = CollectUtils.newHashMap();
		for (MenuProfile profile : menuProfiles) {
			OqlBuilder<Menu> menuQuery = OqlBuilder.from(Menu.class, "menu");
			menuQuery.where("menu.profile=:profile", profile).select("menu.enabled,count(*)")
					.groupBy("enabled");
			menuStats.put(profile.getId(), entityDao.search(menuQuery));
		}
		put("menuProfiles", menuProfiles);
		put("menuStats", menuStats);

		// stat resource
		OqlBuilder<Resource> resourceQuery = OqlBuilder.from(Resource.class, "resource");
		resourceQuery.select("resource.enabled,count(*)").groupBy("enabled");
		put("resourceStat", entityDao.search(resourceQuery));

		// stat pattern and restriction
		put("restrictionStat",
				entityDao.search(OqlBuilder.from(Restriction.class, "pattern").select("count(*)")));
		put("userPropertyMetaStat",
				entityDao.search(OqlBuilder.from(UserPropertyMeta.class, "param").select("count(*)")));
		put("groupPropertyMetaStat",
				entityDao.search(OqlBuilder.from(GroupPropertyMeta.class, "param").select("count(*)")));
		return forward();
	}

	private void populateUserStat() {
		OqlBuilder<GroupMember> userQuery = OqlBuilder.from(GroupMember.class, "gm");
		userQuery.select("gm.group.name,gm.user.enabled,count(*)").groupBy("gm.group.name,gm.user.enabled");
		put("userStat", entityDao.search(userQuery));
	}

}
