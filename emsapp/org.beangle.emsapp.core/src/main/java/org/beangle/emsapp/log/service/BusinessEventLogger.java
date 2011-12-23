/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.log.service;

import org.apache.commons.lang.StringUtils;
import org.beangle.emsapp.event.BusinessEvent;
import org.beangle.emsapp.log.model.BusinessLogBean;
import org.beangle.emsapp.log.model.BusinessLogDetailBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.WebAuthenticationDetails;
import org.springframework.context.ApplicationListener;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 AM chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements ApplicationListener<BusinessEvent> {

	private SessionRegistry sessionRegistry;

	public void onApplicationEvent(BusinessEvent event) {
		BusinessLogBean log = new BusinessLogBean();
		log.setOperateAt(event.getIssueAt());
		log.setOperation(StringUtils.defaultIfBlank(event.getDescription(), "  "));
		log.setResource(StringUtils.defaultIfBlank(event.getResource(), "  "));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null == auth) return;
		log.setOperator(auth.getName());
		Object details = auth.getDetails();
		if ((details instanceof WebAuthenticationDetails)) {
			WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
			log.setIp(webDetails.getAgent().getIp());
			log.setAgent(webDetails.getAgent().getOs() + " " + webDetails.getAgent().getBrowser());
			log.setEntry(sessionRegistry.getResource(webDetails.getSessionId()));
		}
		if (null != event.getDetail()) {
			log.setDetail(new BusinessLogDetailBean(log, event.getDetail()));
		}
		entityDao.saveOrUpdate(log);
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
