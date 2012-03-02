/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.collection.Order;
import org.beangle.collection.page.PageLimit;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.session.model.SessioninfoLogBean;

/**
 * 维护个人账户信息
 * 
 * @author chaostone
 */
public class MyAction extends SecurityActionSupport {

//	private MailSender mailSender;
//
//	private SimpleMailMessage message;

	private SessionRegistry sessionRegistry;

	private UserDashboardHelper userDashboardHelper;

	public String index() {
		userDashboardHelper.buildDashboard(entityDao.get(User.class, getUserId()));
		return forward();
	}

	public String infolet() {
		put("user", entityDao.get(User.class, getUserId()));
		return forward();
	}

	public String dashboard() {
		userDashboardHelper.buildDashboard(entityDao.get(User.class, getUserId()));
		return forward();
	}

	public String activity() {
		OqlBuilder<SessioninfoLogBean> builder = OqlBuilder.from(SessioninfoLogBean.class,
				"sessioninfoLog");
		builder.where("sessioninfoLog.username=:name", getUsername());
		builder.orderBy(Order.parse("sessioninfoLog.loginAt desc"));
		PageLimit limit = getPageLimit();
		limit.setPageSize(10);
		builder.limit(limit);
		put("sessioninfoLogs", entityDao.search(builder));
		put("sessioninfos", sessionRegistry.getSessioninfos(SecurityUtils.getUsername(), true));
		return forward();
	}

	public String resetPassword() {
		return forward();
	}

	/**
	 * 用户修改自己的密码
	 * 
	 * @return
	 */
	public String edit() {
		put("user",entityDao.get(User.class, getUserId()));
		return forward();
	}

	/**
	 * 用户更新自己的密码和邮箱
	 * 
	 * @return
	 */
	public String save() {
		Long userId = getUserId();
		String email = get("mail");
		String pwd = get("password");
		Map<String, Object> valueMap = CollectUtils.newHashMap();
		valueMap.put("password", pwd);
		valueMap.put("mail", email);
		entityDao.update(User.class, "id", new Object[] { userId }, valueMap);
		return redirect("infolet", "ok.passwordChanged");
	}

	/**
	 * 发送密码
	 */
	public String sendPassword() {
		String name = get("name");
		String email = get("mail");
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email)) {
			addActionError("error.parameters.needed");
			return (ERROR);
		}
		List<User> userList = entityDao.get(User.class, "name", name);
		User user = null;
		if (userList.isEmpty()) {
			return goErrorWithMessage("error.user.notExist");
		} else {
			user = userList.get(0);
		}
		if (!StringUtils.equals(email, user.getMail())) {
			return goErrorWithMessage("error.email.notEqualToOrign");
		} else {
			String longinName = user.getName();
			String password = RandomStringUtils.randomNumeric(6);
			user.setRemark(password);
			user.setPassword(EncryptUtil.encode(password));
			String title = getText("user.password.sendmail.title");

			List<Object> values = CollectUtils.newArrayList();
			values.add(longinName);
			values.add(password);
//			String body = getText("user.password.sendmail.body", values);
//			try {
//				SimpleMailMessage msg = new SimpleMailMessage(message);
//				msg.setTo(user.getMail());
//				msg.setSubject(title);
//				msg.setText(body.toString());
//				mailSender.send(msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info("reset password error for user:" + user.getName() + " with email :"
//						+ user.getMail());
//				return goErrorWithMessage("error.email.sendError");
//			}
		}
		entityDao.saveOrUpdate(user);
		return forward("sendResult");
	}

	private String goErrorWithMessage(String key) {
		addError(key);
		return forward("resetPassword");
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

//	public void setMailSender(MailSender mailSender) {
//		this.mailSender = mailSender;
//	}
//
//	public void setMessage(SimpleMailMessage message) {
//		this.message = message;
//	}

	public void setUserDashboardHelper(UserDashboardHelper userDashboardHelper) {
		this.userDashboardHelper = userDashboardHelper;
	}

}
