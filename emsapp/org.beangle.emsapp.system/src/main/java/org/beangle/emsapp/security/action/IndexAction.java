/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;
import java.util.Map;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.security.GroupMember;
import org.beangle.emsapp.security.Resource;
import org.beangle.emsapp.security.nav.Menu;
import org.beangle.emsapp.security.nav.MenuProfile;
import org.beangle.emsapp.security.profile.GroupPropertyMeta;
import org.beangle.emsapp.security.profile.UserPropertyMeta;
import org.beangle.emsapp.security.restrict.Restriction;
import org.beangle.emsapp.web.action.SecurityActionSupport;
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
		userQuery.select("gm.group.code,gm.group.name,gm.user.enabled,count(*)").groupBy(
				"gm.group.name,gm.user.enabled");
		List<?> datas = entityDao.search(userQuery);
		Map<String, Map<Object,Object>> rs = CollectUtils.newHashMap();
		for (Object data : datas) {
			Object[] groupStat = (Object[]) data;
			String key = groupStat[0] + " " + groupStat[1];
			Map<Object,Object> statusMap = rs.get(key);
			if (null == statusMap) {
				statusMap = CollectUtils.newHashMap();
				rs.put(key, statusMap);
			}
			statusMap.put(groupStat[2], groupStat[3]);
		}
		put("userStat", rs);
	}

}
