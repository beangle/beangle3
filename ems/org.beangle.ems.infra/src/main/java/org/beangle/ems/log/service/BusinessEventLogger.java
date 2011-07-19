/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log.service;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.event.BusinessEvent;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.Sessioninfo;
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
		log.setOperater(auth.getPrincipal().toString());
		Object details = auth.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
			log.setIp(webDetails.getAgent().getIp());
			log.setAgent(webDetails.getAgent().toString());
			Sessioninfo activity = sessionRegistry.getSessioninfo(webDetails.getSessionId());
			if (null != activity) {
				log.setEntry(sessionRegistry.getResource(webDetails.getSessionId()));
			}
		}
		entityDao.saveOrUpdate(log);
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
