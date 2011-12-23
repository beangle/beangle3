/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import org.apache.commons.lang.Validate;

/**
 * A <code>ThreadLocal</code>-based implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy}.
 * 
 * @author chaostone
 * @version $Id: ThreadLocalSecurityContextHolderStrategy.java 2217 2007-10-27
 *          00:45:30Z $
 * @see java.lang.ThreadLocal
 * @see org.beangle.security.web.context.security.context.HttpSessionContextIntegrationFilter
 */
public class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

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
		Validate.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}
}
