/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.predicates.ValidEntityKeyPredicate;

public class PasswordAction extends SecurityActionSupport {

	private UserService userService;

	/**
	 * 显示修改用户帐户界面
	 * 
	 * @return
	 */
	public String edit() {
		put("user", getEntity());
		return forward();
	}

	/**
	 * 更新其他用户帐户
	 * 
	 * @return
	 */
	public String save() {
		Long userId = getLong("user.id");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(userId)) {
			User user = userService.get(userId);
			User manager = entityDao.get(User.class, getUserId());
			if (userService.isManagedBy(manager, user)) {
				return updateAccount(userId);
			} else {
				return null;
			}
		} else {
			addError("error.parameters.needed");
			return ERROR;
		}
	}
	public String infolet() {
		put("user", getEntity());
		return forward();
	}

	/**
	 * 更新指定帐户的密码和邮箱
	 * 
	 * @param userId
	 * @return
	 */
	private String updateAccount(Long userId) {
		String email = get("mail");
		String pwd = get("password");
		Map<String, Object> valueMap = CollectUtils.newHashMap();
		valueMap.put("password", pwd);
		valueMap.put("mail", email);
		entityDao.update(User.class, "id", new Object[] { userId }, valueMap);
		return redirect("infolet", "ok.passwordChanged", "&id=" + userId);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected String getEntityName() {
		return User.class.getName();
	}

}
