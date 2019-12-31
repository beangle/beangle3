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

import java.util.Set;

public class Authority {
  final String resourceName;
  final String scope;
  final Set<String> roles;

  public Authority(String resourceName, String scope,Set<String> roles) {
    this.resourceName = resourceName;
    this.scope = scope;
    this.roles=roles;
  }

  public boolean match(String[] authorities){
    for(String a:authorities){
      if(roles.contains(a))return true;
    }
    return false;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getScope() {
    return scope;
  }

  public Set<String> getRoles() {
    return roles;
  }
}
