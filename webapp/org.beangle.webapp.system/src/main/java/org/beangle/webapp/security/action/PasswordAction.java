/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.predicates.ValidEntityKeyPredicate;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.service.UserService;

public class PasswordAction extends SecurityActionSupport {

	private UserService userService;

	/**
	 * 显示修改用户帐户界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editUser() {
		put("user", userService.get(getLong("user.id")));
		return forward();
	}

	/**
	 * 更新其他用户帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateUser() {
		Long userId = getLong("user.id");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(userId)) {
			User user = userService.get(userId);
			User manager = getUser();
			if (userService.isManagedBy(manager, user)) {
				return updateAccount(userId);
			} else {
				return null;
			}
		} else {
			addError("error.parameters.needed");
			return (ERROR);
		}
	}

	/**
	 * 更新指定帐户的密码和邮箱
	 * 
	 * @param mapping
	 * @param request
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
		addMessage("ok.passwordChanged");
		return "actionResult";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
