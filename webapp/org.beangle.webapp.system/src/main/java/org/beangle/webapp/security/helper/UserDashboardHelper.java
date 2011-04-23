/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.helper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.service.RestrictionService;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.blueprint.session.SessionActivity;
import org.beangle.security.core.session.SessionRegistry;
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

	private RestrictionService restrictionService;

	private UserService userService;

	public void buildDashboard(User user) {
		ContextHelper.put("user", user);
		populateMenus(user);
		populateSessionActivities(user);
		populateOnlineActivities(user);
		RestrictionHelper helper = new RestrictionHelper(entityDao);
		helper.setRestrictionService(restrictionService);
		helper.populateInfo(user);
	}

	private void populateOnlineActivities(User user) {
		Collection<?> onlineActivities = sessionRegistry.getSessionInfos(
				new UserToken(user.getId(), user.getName(), user.getFullname(), user.getPassword(), user
						.getDefaultCategory(), user.getStatus() > 0, false, false, false, null), true);
		ContextHelper.put("onlineActivities", onlineActivities);
	}

	private void populateSessionActivities(User user) {
		OqlBuilder<SessionActivity> onlineQuery = OqlBuilder.from(SessionActivity.class, "sessionActivity");
		onlineQuery.where("sessionActivity.name =:name", user.getName());
		PageLimit limit = QueryHelper.getPageLimit();
		limit.setPageSize(5);
		onlineQuery.orderBy("sessionActivity.loginAt desc").limit(limit);
		ContextHelper.put("sessionActivities", entityDao.search(onlineQuery));
	}

	private void populateMenus(User user) {
		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
		query.where("menuProfile.category in(:categories)", user.getCategories());
		List<?> menuProfiles = entityDao.search(query);
		ContextHelper.put("menuProfiles", menuProfiles);

		Long menuProfileId = Params.getLong("menuProfileId");
		if (null == menuProfileId && !menuProfiles.isEmpty()) {
			menuProfileId = ((MenuProfile) (menuProfiles.get(0))).getId();
		}
		if (null != menuProfileId) {
			MenuProfile menuProfile = (MenuProfile) entityDao.get(MenuProfile.class, menuProfileId);
			List<Menu> menus = authorityService.getMenus(menuProfile, user);
			Set<Resource> resources = CollectUtils.newHashSet(authorityService.getResources(user));
			Map<String, Group> groupMap = CollectUtils.newHashMap();
			Map<String, List<Menu>> groupMenusMap = CollectUtils.newHashMap();

			List<Group> myGroups = userService.getGroups(user, GroupMember.Ship.MEMBER);
			for (Group group : myGroups) {
				groupMap.put(group.getId().toString(), group);
				groupMenusMap.put(group.getId().toString(),
						authorityService.getMenus(menuProfile, group, Boolean.TRUE));
			}
			ContextHelper.put("menus", menus);
			ContextHelper.put("groupMap", groupMap);
			ContextHelper.put("groupMenusMap", groupMenusMap);
			ContextHelper.put("resources", resources);
		}
	}

	// public String restrictionInfo() {
	// User user = getUser();
	// return forward(new Action(RestrictionAction.class, "info",
	// "&restriction.holder.id="
	// + user.getId() + "&restrictionType=user"));
	// }

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
