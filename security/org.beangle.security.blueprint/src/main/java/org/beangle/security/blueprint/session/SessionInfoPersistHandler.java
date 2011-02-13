package org.beangle.security.blueprint.session;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.model.entity.Model;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.WebAuthenticationDetails;
import org.beangle.security.web.auth.logout.LogoutHandler;
import org.springframework.beans.factory.InitializingBean;

public class SessionInfoPersistHandler extends BaseServiceImpl implements LogoutHandler, InitializingBean {

	private SessionRegistry sessionRegistry;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(sessionRegistry, "sessionRegistry must be set");
	}

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		SessionActivity record = (SessionActivity) Model.newInstance(SessionActivity.class);
		Object details = auth.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) return;
		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		SessionInfo info = sessionRegistry.getSessionInfo(webDetails.getSessionId());
		if (null == info) return;
		record.setSessionid(info.getSessionid());
		record.setName(auth.getName());
		UserToken token = (UserToken) auth.getPrincipal();
		record.setFullname(token.getFullname());
		record.setCategory(token.getCategory());
		record.setLoginAt(info.getLoginAt());
		record.setLastAccessAt(info.getLastAccessAt());
		record.setLogoutAt(new Timestamp(System.currentTimeMillis()));

		record.setOs(webDetails.getAgent().getOs().toString());
		record.setAgent(webDetails.getAgent().getBrowser().toString());
		record.setHost(webDetails.getAgent().getIp());
		record.setRemark(info.getRemark());
		record.calcOnlineTime();
		entityDao.saveOrUpdate(record);
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
