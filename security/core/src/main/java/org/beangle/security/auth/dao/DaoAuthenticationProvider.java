/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.auth.dao;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.encoding.PasswordEncoder;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoAuthenticationProvider extends AbstractUserDetailAuthenticationProvider {

  private PasswordEncoder passwordEncoder;

  private UserDetailService userDetailService;

  @Override
  protected void additionalAuthenticationChecks(UserDetail user, Authentication auth)
      throws AuthenticationException {
    if (!passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials())) { throw new BadCredentialsException(); }
  }

  @Override
  protected UserDetail retrieveUser(String username, Authentication authentication)
      throws AuthenticationException {
    return userDetailService.loadDetail(username);
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public UserDetailService getUserDetailService() {
    return userDetailService;
  }

  public void setUserDetailService(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }
}
