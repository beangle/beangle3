/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web.auth.preauth;

import org.beangle.security.auth.dao.AbstractUserDetailAuthenticationProvider;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

/**
 * Provider preauthed authentication detail.
 * 
 * @author chaostone
 * @version $Id: PreauthUserDetailProvider.java Oct 29, 2010 8:24:15 AM
 *          chaostone $
 */
public class PreauthUserDetailProvider extends AbstractUserDetailAuthenticationProvider {

  private UserDetailService userDetailService;

  @Override
  protected void additionalAuthenticationChecks(UserDetail userDetails, Authentication authentication)
      throws AuthenticationException {
    // preauthed token,ignore password validation;
  }

  @Override
  protected UserDetail retrieveUser(String username, Authentication authentication)
      throws AuthenticationException {
    return userDetailService.loadDetail(username);
  }

  public boolean supports(Class<? extends Authentication> authentication) {
    return (PreauthAuthentication.class.isAssignableFrom(authentication));
  }

  public void setUserDetailService(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }
}
