/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.beangle.security.web.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.security.web.auth.logout.SecurityContextLogoutHandler;
import org.beangle.web.filter.GenericHttpFilterBean;
import org.beangle.web.util.RedirectUtils;
import org.springframework.util.Assert;

/**
 * Filter required by concurrent session handling package.
 * <p>
 * This filter performs two functions. First, it calls
 * {@link org.beangle.security.core.session.SessionRegistry#refreshLastRequest(String)} for each
 * request so that registered sessions always have a correct "last update" date/time. Second, it
 * retrieves a {@link org.beangle.security.core.session.SessionInformation} from the
 * <code>SessionRegistry</code> for each request and checks if the session has been marked as
 * expired. If it has been marked as expired, the configured logout handlers will be called (as
 * happens with {@link org.beangle.security.web.auth.logout.LogoutFilter}), typically to invalidate
 * the session. A redirect to the expiredURL specified will be performed, and the session
 * invalidation will cause an {@link org.beangle.security.web.session.HttpSessionDestroyedEvent} to
 * be published via the {@link org.beangle.security.web.session.HttpSessionEventPublisher}
 * registered in <code>web.xml</code>.
 * </p>
 * 
 * @author chaostone
 */
public class ConcurrentSessionFilter extends GenericHttpFilterBean {

	private SessionRegistry sessionRegistry;
	private String expiredUrl;
	private LogoutHandlerStack handlerStack=new LogoutHandlerStack(new SecurityContextLogoutHandler());

	@Override
	protected void initFilterBean() {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		Assert.isTrue(expiredUrl == null || RedirectUtils.isValidRedirectUrl(expiredUrl), expiredUrl
				+ " isn't a valid redirect URL");
	}

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			SessionInfo info = sessionRegistry.getSessionInfo(session.getId());
			// Expired - abort processing
			if (null != info) {
				if (info.isExpired()) {
					doLogout(request, response);
					String targetUrl = determineExpiredUrl(request, info);
					if (targetUrl != null) {
						RedirectUtils.sendRedirect(request, response, targetUrl);
						return;
					} else {
						response.getWriter().print(
								"This session has been expired (possibly due to multiple concurrent "
										+ "logins being attempted as the same user).");
						response.flushBuffer();
					}
					return;
				} else {
					// Non-expired - update last request date/time
					info.refreshLastRequest();
				}
			}
		}

		chain.doFilter(request, response);
	}

	protected String determineExpiredUrl(HttpServletRequest request, SessionInfo info) {
		return expiredUrl;
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		handlerStack.logout(request, response, auth);
	}

	public void setExpiredUrl(String expiredUrl) {
		this.expiredUrl = expiredUrl;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setHandlerStack(LogoutHandlerStack handlerStack) {
		this.handlerStack = handlerStack;
	}
}
