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
package org.beangle.security.core.context;

/**
 * A strategy for storing security context information against a thread.
 * <p>
 * The preferred strategy is loaded by
 * {@link org.beangle.security.core.context.SecurityContextHolder}.
 * </p>
 * 
 * @author chaostone
 * @version $Id: SecurityContextHolderStrategy.java 2142 2007-09-21 18:18:21Z $
 */
public interface SecurityContextHolderStrategy {
  /**
   * Clears the current context.
   */
  void clearContext();

  /**
   * Obtains the current context.
   * 
   * @return a context (never <code>null</code> - create a default
   *         implementation if necessary)
   */
  SecurityContext getContext();

  /**
   * Sets the current context.
   * 
   * @param context
   *          to the new argument (should never be <code>null</code>,
   *          although implementations must check if <code>null</code> has
   *          been passed and throw an <code>IllegalArgumentException</code> in such cases)
   */
  void setContext(SecurityContext context);
}
