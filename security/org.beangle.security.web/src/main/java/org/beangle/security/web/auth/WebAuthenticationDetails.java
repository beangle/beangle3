/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beangle.security.core.session.SessionIdAware;
import org.beangle.web.agent.Useragent;
import org.beangle.web.util.RequestUtils;

public class WebAuthenticationDetails implements SessionIdAware, Serializable {

	private static final long serialVersionUID = -8543528078535952987L;

	private String sessionId;
	private Useragent agent;

	public WebAuthenticationDetails(HttpServletRequest request) {
		agent = RequestUtils.getUserAgent(request);
		HttpSession session = request.getSession(true);
		sessionId = (session != null) ? session.getId() : null;
		doPopulateAdditionalInformation(request);
	}

	/**
	 * Provided so that subclasses can populate additional information.
	 * 
	 * @param request
	 *            that the authentication request was received from
	 */
	protected void doPopulateAdditionalInformation(HttpServletRequest request) {
	}

	/**
	 * Indicates the <code>HttpSession</code> id the authentication request was
	 * received from.
	 * 
	 * @return the session ID
	 */
	public String getSessionId() {
		return sessionId;
	}

	public Useragent getAgent() {
		return agent;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString() + ": ");
		sb.append("SessionId: ").append(getSessionId()).append("; ");
		sb.append("RemoteIpAddress: ").append(agent.getIp()).append("; ");
		sb.append("Operation System: ").append(agent.getOs()).append("; ");
		sb.append("Browser: ").append(agent.getBrowser());
		return sb.toString();
	}

}
