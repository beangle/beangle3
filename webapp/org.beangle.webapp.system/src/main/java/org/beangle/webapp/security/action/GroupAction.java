/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.sql.Date;

import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.GroupPropertyExtractor;
import org.beangle.security.blueprint.service.UserService;

/**
 * 用户组信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class GroupAction extends SecurityActionSupport {

	private UserService userService;

	@Override
	protected String getShortName() {
		return "userGroup";
	}

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
		return new GroupPropertyExtractor(getTextResource());
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
	 * @return
	 */
	public String remove() {
		Long[] groupIds = getEntityIds(getShortName());
		User curUser = userService.get(getUserId());
		userService.removeGroup(curUser, entityDao.get(Group.class, groupIds));
		return redirect("search", "info.remove.success");
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
