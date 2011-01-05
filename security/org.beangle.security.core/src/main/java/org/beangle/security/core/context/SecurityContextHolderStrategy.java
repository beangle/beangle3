/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
 * @version $Id: SecurityContextHolderStrategy.java 2142 2007-09-21 18:18:21Z
 *           $
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
	 *            to the new argument (should never be <code>null</code>,
	 *            although implementations must check if <code>null</code> has
	 *            been passed and throw an <code>IllegalArgumentException</code>
	 *            in such cases)
	 */
	void setContext(SecurityContext context);
}
