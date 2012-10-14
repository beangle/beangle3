/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
