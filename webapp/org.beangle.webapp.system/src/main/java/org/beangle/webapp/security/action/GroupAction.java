/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.sql.Date;
import java.util.List;

import org.beangle.commons.lang.SeqStrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.GroupPropertyExtractor;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.struts2.action.ActionTextResource;

/**
 * 用户组信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class GroupAction extends SecurityActionSupport {

	private UserService userService;

	protected void indexSetting() {
		put("categories", entityDao.getAll(UserCategory.class));
	}

	protected void editSetting(Entity<?> entity) {
		put("categories", entityDao.getAll(UserCategory.class));
	}

	protected OqlBuilder<Group> getQueryBuilder() {
		User manager = getUser();
		OqlBuilder<Group> entityQuery = OqlBuilder.from(entityName, "userGroup");
		if (!isAdmin(manager)) {
			entityQuery.join("userGroup.managers", "manager");
			entityQuery.where("manager.id=:managerId", manager.getId());
		}
		populateConditions(entityQuery);
		entityQuery.limit(getPageLimit()).orderBy(get("orderBy"));
		return entityQuery;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new GroupPropertyExtractor(new ActionTextResource(this));
	}

	protected String saveAndForward(Entity<?> entity) {
		Group group = (Group) entity;
		if (entityDao.duplicate(Group.class, group.getId(), "name", group.getName())) { return redirect(
				"edit", "error.notUnique"); }
		if (!group.isPersisted()) {
			User creator = userService.get(getUserId());
			userService.createGroup(creator, group);
		} else {
			group.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (!group.isPersisted()) {
				group.setCreatedAt(new Date(System.currentTimeMillis()));
			}
			entityDao.saveOrUpdate(group);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 删除一个或多个用户组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String remove() {
		String groupIdSeq = get("groupIds");
		User curUser = userService.get(getUserId());
		List<Group> toBeRemoved = entityDao.get(Group.class,
				SeqStrUtils.transformToLong(groupIdSeq));
		userService.removeGroup(curUser, toBeRemoved);
		return redirect("search", "info.delete.success");
	}

	/**
	 * 设置拷贝权限的起始用户组和目标用户组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String copyAuthSetting() {
		Long fromGroupId = getLong("groupId");
		Group fromGroup = entityDao.get(Group.class, fromGroupId);
		put("fromGroup", fromGroup);
		// put("toGroups", getUser().getMngGroups());
		return forward();
	}

	/**
	 * 拷贝权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String copyAuth() {
		// Long fromGroupId = getLong("fromGroupId");
		// Long[] toGroupIds = SeqStringUtil.transformToLong(get("toGroupIds"));
		// Group fromGroup = groupService.get(fromGroupId);
		// List toGroups = groupService.get(toGroupIds);
		// authorityService.copyAuthority(fromGroup, toGroups);
		return redirect("search", "info.set.success");
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
