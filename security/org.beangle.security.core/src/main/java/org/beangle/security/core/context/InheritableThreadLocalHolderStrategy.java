/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
 * An <code>InheritableThreadLocal</code>-based implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy}.
 * 
 * @author chaostone
 * @see java.lang.ThreadLocal
 */
public class InheritableThreadLocalHolderStrategy implements SecurityContextHolderStrategy {

  private static ThreadLocal<SecurityContext> contextHolder = new InheritableThreadLocal<SecurityContext>();

  public void clearContext() {
    contextHolder.set(null);
  }

  public SecurityContext getContext() {
    if (contextHolder.get() == null) contextHolder.set(new SecurityContextBean());
    return contextHolder.get();
  }

  public void setContext(SecurityContext context) {
    Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    contextHolder.set(context);
  }
}
