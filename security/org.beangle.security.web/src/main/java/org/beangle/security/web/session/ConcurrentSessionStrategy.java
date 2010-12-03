/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.session.SessionController;
import org.beangle.security.core.session.SessionException;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentSessionStrategy implements SessionStrategy {

	private final Logger logger = LoggerFactory.getLogger(ConcurrentSessionStrategy.class);

	protected SessionRegistry sessionRegistry;

	protected SessionController sessionController;

	private boolean exceptionIfMaximumExceeded = false;

	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionException {
		HttpSession session = request.getSession();
		checkAuthenticationAllowed(authentication, session);
		sessionRegistry.register(session.getId(), authentication);
	}

	/**
	 * allowableSessionsExceeded
	 * 
	 * @param sessionId
	 * @param sessions
	 * @param allowableSessions
	 * @param registry
	 * @see checkAuthenticationAllowed
	 */
	protected void allowableSessionsExceeded(String sessionId, List<SessionInfo> sessions,
			int allowableSessions, SessionRegistry registry) throws SessionException {
		if (exceptionIfMaximumExceeded || (sessions == null)) { throw new SessionException(
				"over max online sessions "); }
		// Determine least recently used session, and mark it for invalidation
		SessionInfo leastRecentlyUsed = null;
		for (int i = 0; i < sessions.size(); i++) {
			if ((leastRecentlyUsed == null)
					|| sessions.get(i).getLastAccessAt()
							.before(leastRecentlyUsed.getLastAccessAt())) {
				leastRecentlyUsed = (SessionInfo) sessions.get(i);
			}
		}

		leastRecentlyUsed.expireNow();
	}

	private void checkAuthenticationAllowed(Authentication request, HttpSession session)
			throws AuthenticationException {
		Object principal = request.getPrincipal();
		String sessionId = session.getId();
		List<SessionInfo> sessions = sessionRegistry.getSessionInfos(principal, false);
		int sessionCount = 0;
		if (sessions != null) {
			sessionCount = sessions.size();
		}
		int allowableSessions = getMaximumSessionsForThisUser(request);
		if (sessionCount < allowableSessions) {
			return;
		} else if (allowableSessions == -1) {
			return;
		} else if (sessionCount == allowableSessions) {
			for (int i = 0; i < sessionCount; i++) {
				if ((sessions.get(i)).getSessionid().equals(sessionId)) { return; }
			}
		}
		allowableSessionsExceeded(sessionId, sessions, allowableSessions, sessionRegistry);
	}

	/**
	 * 根据用户身份确定单个用户的最大会话数<br>
	 * Method intended for use by subclasses to override the maximum number of
	 * sessions that are permitted for a particular authentication.
	 * 
	 * @param authentication
	 *            to determine the maximum sessions for
	 * @return either -1 meaning unlimited, or a positive integer to limit
	 *         (never zero)
	 */
	protected int getMaximumSessionsForThisUser(Authentication auth) {
		return sessionController.getMaximumSessions(auth);
	}

	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setSessionController(SessionController sessionController) {
		this.sessionController = sessionController;
	}

}
