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
package org.beangle.security.core.userdetail;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

/**
 * Provides core user information.
 */
public interface Account extends Serializable, Principal {
  String getName();

  String getRemoteToken();

  String getDescription();

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  Map<String, Object> getDetails();

  /**
   * Indicates whether the user's account has expired. An expired account
   * cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie
   *         non-expired), <code>false</code> if no longer valid (ie expired)
   */
  boolean isAccountExpired();

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be
   * authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  boolean isAccountLocked();

  /**
   * Indicates whether the user's credentials (password) has expired. Expired
   * credentials prevent authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie
   *         non-expired), <code>false</code> if no longer valid (ie expired)
   */
  boolean isCredentialExpired();

  /**
   * Check whether credential is ReadOnly
   * @return
   */
  boolean isCredentialReadOnly();
  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot
   * be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  boolean isEnabled();

  int getCategoryId();

  Profile[] getProfiles() ;
}
