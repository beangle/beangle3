/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.commons.web.util.RedirectUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.security.web.auth.logout.SecurityContextLogoutHandler;

/**
 * Filter required by concurrent session handling package.
 * <p>
 * This filter performs two functions. First, it calls
 * {@link org.beangle.security.core.session.SessionRegistry#access(String,String,long)} for each
 * request so that registered sessions always have a correct "last update" date/time. Second, it
 * retrieves a {@link org.beangle.security.core.session.Sessioninfo} from the
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
public class ConcurrentSessionFilter extends GenericHttpFilter {

  private SessionRegistry sessionRegistry;
  private String expiredUrl;
  private LogoutHandlerStack handlerStack = new LogoutHandlerStack(new SecurityContextLogoutHandler());

  @Override
  protected void initFilterBean() {
    Assert.notNull(sessionRegistry, "SessionRegistry required");
    Assert.isTrue(expiredUrl == null || RedirectUtils.isValidRedirectUrl(expiredUrl), expiredUrl
        + " isn't a valid redirect URL");
  }

  /**
   * 没有登录或匿名账户不进行session处理
   * 
   * @param request
   */
  protected boolean shouldCare(HttpServletRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return null != auth && !(AnonymousAuthentication.class.isAssignableFrom(auth.getClass()));
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    HttpSession session = request.getSession(false);
    SessionStatus info = null;
    if (session != null && shouldCare(request)) {
      info = sessionRegistry.getSessionStatus(session.getId());
      // Expired - abort processing
      if (null != info) {
        if (info.isExpired()) {
          doLogout(request, response);
          sessionRegistry.remove(session.getId());
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
        }
      }
    }
    String uri = null;
    if (null != info) {
      uri = RequestUtils.getServletPath(request);
      sessionRegistry.access(session.getId(), uri, System.currentTimeMillis());
    }
    try {
      chain.doFilter(request, response);
    } finally {
      if (null != info) sessionRegistry.endAccess(session.getId(), uri, System.currentTimeMillis());
    }
  }

  protected String determineExpiredUrl(HttpServletRequest request, SessionStatus info) {
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
