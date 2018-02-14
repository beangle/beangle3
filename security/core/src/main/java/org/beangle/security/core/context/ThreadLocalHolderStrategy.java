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
package org.beangle.security.core.context;

import org.beangle.commons.lang.Assert;

/**
 * A <code>ThreadLocal</code>-based implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy}.
 *
 * @author chaostone
 * @see java.lang.ThreadLocal
 */
public class ThreadLocalHolderStrategy implements SecurityContextHolderStrategy {

  private static ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();

  public void clearContext() {
    contextHolder.set(null);
  }

  public SecurityContext getContext() {
    if (contextHolder.get() == null) {
      contextHolder.set(new SecurityContextBean());
    }
    return contextHolder.get();
  }

  public void setContext(SecurityContext context) {
    Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    contextHolder.set(context);
  }
}
