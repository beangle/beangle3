/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log.service;

import java.util.Date;

import org.beangle.ems.event.BusinessEvent;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.auth.WebAuthenticationDetails;
import org.springframework.context.ApplicationListener;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 AM chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements ApplicationListener<BusinessEvent> {

	public void onApplicationEvent(BusinessEvent event) {
		BusinessLogBean log = new BusinessLogBean();
		log.setOperateAt(new Date());
		log.setOperation(event.toString());
		log.setOperater(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		Object details=SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (!(details instanceof WebAuthenticationDetails)) return;
		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		
//		log.setResource(SecurityUtils.getResource());
//		log.setEntry(entry);

		SecurityContextHolder.getContext().getAuthentication().getDetails();
		log.setIp(webDetails.getAgent().getIp());
		log.setParams(webDetails.getAgent().toString());
	}

}
