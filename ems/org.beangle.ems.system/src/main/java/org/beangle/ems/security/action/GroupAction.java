/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.sql.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.EqualPredicate;
import org.beangle.dao.Entity;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.dao.util.HierarchyEntityUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.helper.GroupPropertyExtractor;
import org.beangle.ems.security.profile.GroupProfile;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.GroupService;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityEntityActionSupport;
import org.beangle.transfer.exporter.PropertyExtractor;

/**
 * 用户组信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class GroupAction extends SecurityEntityActionSupport {

	private GroupService groupService;

	private UserService userService;

	@Override
	protected String getShortName() {
		return "userGroup";
	}

	/**
	 * 对组可管理意为<br>
	 * 1 建立下级组
	 * 2 移动下级组顺序
	 * 不能改变组的1）动态属性、2）权限和3）直接成员，4）删除组，5）重命名，这些将看作组同部分一起看待的。
	 * 只要拥有上级组的管理权限，才能变更这些，这些称之为写权限。
	 * 成员关系可以等价于读权限
	 * 授权关系可以等价于读权限传播
	 * 拥有某组的管理权限，不意味拥有下级组的管理权限。新建组情况自动授予该组的其他管理者管理权限。
	 */
	protected void editSetting(Entity<?> entity) {
		OqlBuilder<Group> query = OqlBuilder.from(getEntityName(), "userGroup");
		if (!isAdmin()) {
			query.join("userGroup.members", "gm");
			// 列举所有成员
			query.where("gm.user.id=:me", getUserId());
		}
		List<Group> groups = entityDao.search(query);
		Group group = (Group) entity;
		groups.removeAll(HierarchyEntityUtils.getFamily(group));
		CollectionUtils.filter(groups, new BeanPredicate("dynamic", new EqualPredicate(Boolean.FALSE)));
		put("parents", groups);
	}

	public String profile() {
		OqlBuilder<GroupProfile> builder = OqlBuilder.from(GroupProfile.class, "gp");
		builder.where("gp.group.id=:groupId", getId("group"));
		put("profiles", entityDao.search(builder));
		return forward();
	}

	protected OqlBuilder<Group> getQueryBuilder() {
		OqlBuilder<Group> entityQuery = OqlBuilder.from(getEntityName(), "userGroup");
		if (!isAdmin()) {
			entityQuery.join("userGroup.members", "gm");
			entityQuery.where("gm.user.id=:me and gm.manager=true", getUserId());
		}
		populateConditions(entityQuery);
		String orderBy = get("orderBy");
		if (null == orderBy) orderBy = "userGroup.code";
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
			if (!group.isPersisted()) group.setCreatedAt(new Date(System.currentTimeMillis()));
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
		Long[] groupIds = getIds(getShortName());
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
