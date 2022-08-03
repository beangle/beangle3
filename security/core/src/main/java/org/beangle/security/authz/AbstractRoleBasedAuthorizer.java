/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.authz;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.security.Request;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.userdetail.DefaultAccount;
import org.beangle.security.util.SessionDaemon;

public abstract class AbstractRoleBasedAuthorizer implements Authorizer, Initializing {

  protected AuthorityDomain domain = AuthorityDomain.empty();

  protected boolean unknownIsProtected = true;

  protected int refreshSeconds = 5 * 60;

  /**
   * 资源是否被授权<br>
   * 1)检查是否是属于公有资源<br>
   * 2)检查角色权限<br>
   */
  public boolean isPermitted(SecurityContext context) {
    return isPermitted(context, context.getRequest());
  }

  @Override
  public boolean isPermitted(SecurityContext context, Request request) {
    boolean isRoot = context.isRoot();
    if (isRoot) return true;

    String resourceName = request.getResource().toString();
    Authority authority = domain.authorities.get(resourceName);
    if (null == authority) {
      if (unknownIsProtected) return context.isValid();
      else return false;
    } else {
      if (authority.scope.equals("Public")) {
        return true;
      } else if (authority.scope.equals("Protected")) {
        return context.isValid();
      } else {
        Session session = context.getSession();
        if (null == session) {
          return false;
        } else {
          DefaultAccount account = (DefaultAccount) session.getPrincipal();
          return authority.match(account.getAuthorities());
        }
      }
    }
  }

  @Override
  public String getScope(String resourceName) {
    Authority au = domain.authorities.get(resourceName);
    if (null == au) {
      if (unknownIsProtected) {
        return "Protected";
      } else {
        return "Private";
      }
    } else {
      return au.scope;
    }
  }

  @Override
  public boolean isRoot(String user) {
    return domain.roots.contains(user);
  }

  @Override
  public void init() throws Exception {
    SessionDaemon.start("Beangle Authority", refreshSeconds, new DomainFetcher(this));
  }

  protected abstract AuthorityDomain fetchDomain();

  public void setDomain(AuthorityDomain domain) {
    this.domain = domain;
  }

  public void setUnknownIsProtected(boolean unknownIsProtected) {
    this.unknownIsProtected = unknownIsProtected;
  }

  public void setRefreshSeconds(int refreshSeconds) {
    this.refreshSeconds = refreshSeconds;
  }
}
