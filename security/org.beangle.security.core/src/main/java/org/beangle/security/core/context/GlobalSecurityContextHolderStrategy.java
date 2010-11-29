/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import org.apache.commons.lang.Validate;

/**
 * A <code>static</code> field-based implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy}.
 * <p>
 * This means that all instances in the JVM share the same
 * <code>SecurityContext</code>. This is generally useful with rich clients,
 * such as Swing.
 * </p>
 * 
 * @author chaostone
 */
public class GlobalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

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
		Validate.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder = context;
	}
}
