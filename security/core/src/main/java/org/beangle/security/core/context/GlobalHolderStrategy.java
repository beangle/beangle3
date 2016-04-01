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
package org.beangle.security.core.context;

import org.beangle.commons.lang.Assert;

/**
 * A <code>static</code> field-based implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy}.
 * <p>
 * This means that all instances in the JVM share the same <code>SecurityContext</code>. This is
 * generally useful with rich clients, such as Swing.
 * </p>
 * 
 * @author chaostone
 */
public class GlobalHolderStrategy implements SecurityContextHolderStrategy {

  private static SecurityContext contextHolder;

  public void clearContext() {
    contextHolder = null;
  }

  public SecurityContext getContext() {
    if (contextHolder == null) {
      contextHolder = new SecurityContextBean();
    }

    return contextHolder;
  }

  public void setContext(SecurityContext context) {
    Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    contextHolder = context;
  }
}
