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
package org.beangle.security.core;

import java.io.Serializable;

/**
 * Represents an authority granted to an {@link Authentication} object.
 */
public interface GrantedAuthority extends Serializable, Comparable<GrantedAuthority> {
  /**
   * If the <code>GrantedAuthority</code> can be represented as a <code>String</code> and that
   * <code>String</code> is sufficient in
   * precision to be relied upon for an access control decision by an AuthorityManager
   * (or delegate), this method should return
   * such a <code>String</code>.
   * <p>
   * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision as a
   * <code>String</code>, <code>null</code> should be returned. Returning <code>null</code> will
   * require an <code>AccessDecisionManager</code> (or delegate) to specifically support the
   * <code>GrantedAuthority</code> implementation, so returning <code>null</code> should be avoided
   * unless actually required.
   * </p>
   * 
   * @return a representation of the granted authority (or <code>null</code> if the granted
   *         authority cannot be expressed as a <code>String</code> with sufficient precision).
   */
  Object getAuthority();
}
