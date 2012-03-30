/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.util.List;
import java.util.Map;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Member;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.profile.PropertyMeta;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.web.action.SecurityActionSupport;

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
		put("propertyMetaStat",
				entityDao.search(OqlBuilder.from(PropertyMeta.class, "param").select("count(*)")));
		return forward();
	}

	private void populateUserStat() {
		OqlBuilder<Member> userQuery = OqlBuilder.from(Member.class, "gm");
		userQuery.select("gm.role.code,gm.role.name,gm.user.enabled,count(*)").groupBy(
				"gm.role.name,gm.user.enabled");
		List<?> datas = entityDao.search(userQuery);
		Map<String, Map<Object, Object>> rs = CollectUtils.newHashMap();
		for (Object data : datas) {
			Object[] roleStat = (Object[]) data;
			String key = roleStat[0] + " " + roleStat[1];
			Map<Object, Object> statusMap = rs.get(key);
			if (null == statusMap) {
				statusMap = CollectUtils.newHashMap();
				rs.put(key, statusMap);
			}
			statusMap.put(roleStat[2], roleStat[3]);
		}
		put("userStat", rs);
	}

}
