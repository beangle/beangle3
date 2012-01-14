/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.sql.Date;
import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.GroupService;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.ems.security.helper.GroupPropertyExtractor;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.model.util.HierarchyEntityUtils;

/**
 * 用户组信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class GroupAction extends SecurityActionSupport {

	private GroupService groupService;

	private UserService userService;

	@Override
	protected String getShortName() {
		return "userGroup";
	}

	protected void editSetting(Entity<?> entity) {
		List<Group> groups = entityDao.getAll(Group.class);
		Group group = (Group) entity;
		groups.removeAll(HierarchyEntityUtils.getFamily(group));
		put("parents", groups);
	}

	protected OqlBuilder<Group> getQueryBuilder() {
		OqlBuilder<Group> entityQuery = OqlBuilder.from(getEntityName(), "userGroup");
		if (!isAdmin()) {
			entityQuery.join("userGroup.members", "gm");
			entityQuery.where("gm.user.id=:me and gm.manager=true", getUserId());
		}
		populateConditions(entityQuery);
		String orderBy=get("orderBy");
		if(null==orderBy)orderBy="userGroup.code";
		entityQuery.limit(getPageLimit()).orderBy(orderBy);
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
			group.setCode("tmp");
			groupService.createGroup(creator, group);
		} else {
			group.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (!group.isPersisted()) {
				group.setCreatedAt(new Date(System.currentTimeMillis()));
			}
			entityDao.saveOrUpdate(group);
		}
		Long newParentId = getLong("parent.id");
		int indexno = getInteger("indexno");
		Group parent = null;
		if (null != newParentId) {
			parent = entityDao.get(Group.class, newParentId);
		}
		groupService.moveGroup(group, parent, indexno);
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
		groupService.removeGroup(curUser, entityDao.get(Group.class, groupIds));
		return redirect("search", "info.remove.success");
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Override
	protected String getEntityName() {
		return Group.class.getName();
	}

}
