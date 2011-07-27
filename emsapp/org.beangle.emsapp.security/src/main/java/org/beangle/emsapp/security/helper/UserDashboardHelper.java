/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.helper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.service.MenuService;
import org.beangle.ems.security.restrict.service.RestrictionService;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.session.model.SessioninfoLogBean;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;

/**
 * @author chaostone
 * @version $Id: DashboardHelper.java Nov 3, 2010 5:19:42 PM chaostone $
 */
public class UserDashboardHelper {

	private EntityDao entityDao;

	private SessionRegistry sessionRegistry;

	private AuthorityService authorityService;

	private MenuService menuService;

	private RestrictionService restrictionService;

	private UserService userService;

	public void buildDashboard(User user) {
		ContextHelper.put("user", user);
		populateMenus(user);
		populateSessioninfoLogs(user);
		populateOnlineActivities(user);
		RestrictionHelper helper = new RestrictionHelper(entityDao);
		helper.setRestrictionService(restrictionService);
		helper.populateInfo(user);
	}

	private void populateOnlineActivities(User user) {
		Collection<?> onlineActivities = sessionRegistry.getSessioninfos(user.getName(), true);
		ContextHelper.put("onlineActivities", onlineActivities);
	}

	private void populateSessioninfoLogs(User user) {
		OqlBuilder<SessioninfoLogBean> onlineQuery = OqlBuilder.from(SessioninfoLogBean.class,
				"sessioninfoLog");
		onlineQuery.where("sessioninfoLog.username = :username", user.getName());
		PageLimit limit = QueryHelper.getPageLimit();
		limit.setPageSize(5);
		onlineQuery.orderBy("sessioninfoLog.loginAt desc").limit(limit);
		ContextHelper.put("sessioninfoLogs", entityDao.search(onlineQuery));
	}

	private void populateMenus(User user) {
		List<?> menuProfiles = menuService.getProfiles(user);
		ContextHelper.put("menuProfiles", menuProfiles);
		Long menuProfileId = Params.getLong("menuProfileId");
		if (null == menuProfileId && !menuProfiles.isEmpty()) {
			menuProfileId = ((MenuProfile) (menuProfiles.get(0))).getId();
		}
		if (null != menuProfileId) {
			MenuProfile menuProfile = (MenuProfile) entityDao.get(MenuProfile.class, menuProfileId);
			List<Menu> menus = menuService.getMenus(menuProfile, user);
			Set<Resource> resources = CollectUtils.newHashSet(authorityService.getResources(user));
			Map<Group, List<Menu>> groupMenusMap = CollectUtils.newHashMap();

			List<Group> myGroups = userService.getGroups(user, GroupMember.Ship.MEMBER);
			for (Group group : myGroups) {
				groupMenusMap.put(group, menuService.getMenus(menuProfile, group, Boolean.TRUE));
			}
			ContextHelper.put("menus", menus);
			ContextHelper.put("groupMenusMap", groupMenusMap);
			ContextHelper.put("resources", resources);
		}
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}

}
