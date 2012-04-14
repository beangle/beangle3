/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.collection.Order;
import org.beangle.dao.Entity;
import org.beangle.dao.Operation;
import org.beangle.dao.query.builder.Condition;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Member;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.User;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.security.helper.UserPropertyExtractor;
import org.beangle.ems.security.model.MemberBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityEntityActionSupport;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.struts2.convention.route.Action;
import org.beangle.transfer.exporter.PropertyExtractor;

/**
 * 用户管理响应处理类
 * 
 * @author chaostone 2005-9-29
 */
public class UserAction extends SecurityEntityActionSupport {

	private UserService userService;

	private UserDashboardHelper userDashboardHelper;

	public String dashboard() {
		Long userId = getId("user");
		User managed = null;
		if (null != userId) {
			managed = entityDao.get(User.class, userId);
		} else {
			String username = get("user.name");
			if (null != username) managed = userService.get(username);
		}
		User me = entityDao.get(User.class, getUserId());
		if (null != managed) {
			if (me.equals(managed) || userService.isManagedBy(me, managed)) {
				userDashboardHelper.buildDashboard(managed);
				return forward();
			} else {
				return forward(ERROR);
			}
		} else {
			userDashboardHelper.buildDashboard(me);
		}
		return forward();
	}

	protected OqlBuilder<User> getQueryBuilder() {
		User manager = entityDao.get(User.class, getUserId());
		OqlBuilder<User> userQuery = OqlBuilder.from(getEntityName(), "user");
		// 查询角色
		StringBuilder sb = new StringBuilder("exists(from user.members m where ");
		List<Object> params = CollectUtils.newArrayList();
		boolean queryRole = false;
		if (!isAdmin()) {
			List<Member> members = userService.getMembers(manager, Member.Ship.GRANTER);
			List<Role> mngRoles = CollectUtils.newArrayList();
			for (Member m : members) {
				mngRoles.add(m.getRole());
			}
			if (mngRoles.isEmpty()) {
				sb.append("1=0");
			} else {
				sb.append("m.role in(:roles) ");
				params.add(mngRoles);
			}
			queryRole = true;
		}
		String roleName = get("roleName");
		if (StringUtils.isNotEmpty(roleName)) {
			if (queryRole) {
				sb.append(" and ");
			}
			sb.append("m.role.name like :roleName ");
			params.add("%" + roleName + "%");
			queryRole = true;
		}
		if (queryRole) {
			sb.append(')');
			Condition roleCondition = new Condition(sb.toString());
			roleCondition.params(params);
			userQuery.where(roleCondition);
		}

		Long categoryId = getLong("categoryId");
		if (null != categoryId) {
			userQuery.join("user.categories", "category");
			userQuery.where("category.id=:categoryId", categoryId);
		}
		populateConditions(userQuery);
		userQuery.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
		return userQuery;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new UserPropertyExtractor(getTextResource());
	}

	/**
	 * 保存用户信息
	 */
	protected String saveAndForward(Entity<?> entity) {
		User user = (User) entity;
		if (entityDao.duplicate(User.class, user.getId(), "name", user.getName())) {
			addFlashMessageNow("security.error.usernameNotAvaliable", user.getName());
			return forward(new Action(this, "edit"));
		}
		String errorMsg = "";
		// 检验用户合法性
		errorMsg = checkUser(user);
		if (StringUtils.isNotEmpty(errorMsg)) { return forward(new Action("edit"), errorMsg); }
		processPassword(user);
		if (!user.isPersisted()) {
			User creator = userService.get(getUserId());
			userService.createUser(creator, user);
		} else {
			userService.saveOrUpdate(user);
		}
		updateUserRole(user);
		return redirect("search", "info.save.success");
	}

	private void updateUserRole(User user) {
		Set<Member> userMembers = user.getMembers();
		Map<Role, MemberBean> memberMap = CollectUtils.newHashMap();
		for (Member gm : userMembers) {
			memberMap.put(gm.getRole(), (MemberBean)gm);
		}
		Set<Member> newMembers = CollectUtils.newHashSet();
		Set<Member> removedMembers = CollectUtils.newHashSet();
		User manager = entityDao.get(User.class, getUserId());
		Collection<Member> members = userService.getMembers(manager, Member.Ship.GRANTER);
		for (Member member : members) {
			MemberBean myMember = memberMap.get(member.getRole());
			boolean isMember = getBool("member" + member.getRole().getId());
			boolean isGranter = getBool("granter" + member.getRole().getId());
			boolean isManager = getBool("manager" + member.getRole().getId());
			if (!isMember && !isGranter && !isManager) {
				if (null != myMember) {
					user.getMembers().remove(myMember);
					removedMembers.add(myMember);
				}
			} else {
				if (null == myMember) {
					myMember = new MemberBean(member.getRole(), user, null);
				}
				myMember.setUpdatedAt(new Date());
				myMember.setMember(isMember);
				myMember.setGranter(isGranter);
				myMember.setManager(isManager);
				newMembers.add(myMember);
			}
		}
		entityDao.execute(Operation.saveOrUpdate(newMembers).remove(removedMembers));
	}

	protected void editSetting(Entity<?> entity) {
		User manager = entityDao.get(User.class, getUserId());
		Set<Role> roles=CollectUtils.newHashSet();
		Map<Role, Member> curMemberMap = CollectUtils.newHashMap();
		Collection<Member> members = userService.getMembers(manager, Member.Ship.GRANTER);
		for (Member gm : members) {
			roles.add(gm.getRole());
			curMemberMap.put(gm.getRole(), gm);
		}
		put("roles", roles);

		User user = (User) entity;
		Set<Member> userMembers = user.getMembers();
		Map<Role, Member> memberMap = CollectUtils.newHashMap();
		for (Member gm : userMembers) {
			memberMap.put(gm.getRole(), gm);
		}
		put("memberMap", memberMap);
		put("curMemberMap", curMemberMap);
		put("isadmin", userService.isRoot(user));
		put("isme", getUserId().equals(user.getId()));
	}

	/**
	 * 删除一个或多个用户
	 * 
	 * @return
	 */
	public String remove() {
		Long[] userIds = getIds("user");
		User creator = userService.get(getUserId());
		List<User> toBeRemoved = userService.getUsers(userIds);
		StringBuilder sb = new StringBuilder();
		User removed = null;
		int success = 0;
		int expected = toBeRemoved.size();
		try {
			for (User one : toBeRemoved) {
				removed = one;
				// 不能删除自己
				if (!one.getId().equals(getUserId())) {
					userService.removeUser(creator, one);
					success++;
				} else {
					addFlashError("security.info.cannotRemoveSelf");
					expected--;
				}
			}
		} catch (Exception e) {
			sb.append(',').append(removed.getName());
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(0);
			addFlashMessage("security.info.userRemovePartial", success, sb);
		} else if (expected == success && success > 0) {
			addFlashMessage("info.remove.success");
		}
		return redirect("search");
	}

	/**
	 * 禁用或激活一个或多个用户
	 * 
	 * @return
	 */
	public String activate() {
		Long[] userIds = getIds("user");
		String isActivate = get("isActivate");
		int successCnt;
		User manager = userService.get(getUserId());
		String msg = "security.info.freeze.success";
		if (StringUtils.isNotEmpty(isActivate) && "false".equals(isActivate)) {
			successCnt = userService.updateState(manager, userIds, false);
		} else {
			msg = "security.info.activate.success";
			successCnt = userService.updateState(manager, userIds, true);
		}
		addFlashMessage(msg, successCnt);
		return redirect("search");
	}

	protected String checkUser(User user) {
		if (!user.isPersisted() && entityDao.exist(getEntityName(), "name", user.getName())) { return "error.model.existed"; }
		return "";
	}

	public String info() throws Exception {
		String name = get("name");
		if (StringUtils.isNotBlank(name)) {
			User user = userService.get(name);
			if (null != user) {
				put("user", user);
				return forward(new Action((Class<?>) null, "dashboard", "&user.id=" + user.getId()));
			} else {
				return null;
			}
		} else {
			return super.info();
		}
	}

	protected void processPassword(User user) {
		String password = get("password");
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(EncryptUtil.encode(password));
		} else if (!user.isPersisted()) {
			password = User.DEFAULT_PASSWORD;
			user.setPassword(EncryptUtil.encode(password));
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserDashboardHelper(UserDashboardHelper userDashboardHelper) {
		this.userDashboardHelper = userDashboardHelper;
	}

	@Override
	protected String getEntityName() {
		return User.class.getName();
	}

}
