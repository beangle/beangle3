/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

/**
 * Associates a given {@link SecurityContext} with the current execution thread.
 * <p>
 * This class provides a series of static methods that delegate to an instance
 * of {@link org.beangle.security.core.context.SecurityContextHolderStrategy} .
 * The purpose of the class is to provide a convenient way to specify the
 * strategy that should be used for a given JVM. This is a JVM-wide setting,
 * since everything in this class is <code>static</code> to facilitate ease of
 * use in calling code.
 * </p>
 * <p>
 * To specify which strategy should be used, you must provide a mode setting. A
 * mode setting is one of the three valid <code>MODE_</code> settings defined as
 * <code>static final</code> fields, or a fully qualified classname to a
 * concrete implementation of
 * {@link org.beangle.security.core.context.SecurityContextHolderStrategy} that
 * provides a public no-argument constructor.
 * </p>
 * <p>
 * There are two ways to specify the desired strategy mode <code>String</code>.
 * The first is to specify it via the system property keyed on
 * {@link #SYSTEM_PROPERTY}. The second is to call
 * {@link #setStrategyName(String)} before using the class. If neither approach
 * is used, the class will default to using {@link #MODE_THREADLOCAL}, which is
 * backwards compatible, has fewer JVM incompatibilities and is appropriate on
 * servers (whereas {@link #MODE_GLOBAL} is definitely inappropriate for server
 * use).
 * </p>
 * 
 * @author chaostone
 * @version $Id: SecurityContextHolder.java 2217 2007-10-27 00:45:30Z  $
 * @see org.beangle.security.web.context.security.context.HttpSessionContextIntegrationFilter
 */
public class SecurityContextHolder {

	public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
	public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
	public static final String MODE_GLOBAL = "MODE_GLOBAL";
	public static final String SYSTEM_PROPERTY = "beangle.security.strategy";
	private static String strategyName = System.getProperty(SYSTEM_PROPERTY);
	private static SecurityContextHolderStrategy strategy;
	private static int initializeCount = 0;

	static {
		initialize();
	}

	/**
	 * Explicitly clears the context value from the current thread.
	 */
	public static void clearContext() {
		strategy.clearContext();
	}

	/**
	 * Obtain the current <code>SecurityContext</code>.
	 * 
	 * @return the security context (never <code>null</code>)
	 */
	public static SecurityContext getContext() {
		return strategy.getContext();
	}

	/**
	 * Primarily for troubleshooting purposes, this method shows how many times
	 * the class has reinitialized its
	 * <code>SecurityContextHolderStrategy</code>.
	 * 
	 * @return the count (should be one unless you've called
	 *         {@link #setStrategyName(String)} to switch to an alternate
	 *         strategy.
	 */
	public static int getInitializeCount() {
		return initializeCount;
	}

	private static void initialize() {
		// Set default
		if ((strategyName == null) || "".equals(strategyName)) {
			strategyName = MODE_THREADLOCAL;
		}
		if (strategyName.equals(MODE_THREADLOCAL)) {
			strategy = new ThreadLocalSecurityContextHolderStrategy();
		} else if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
			strategy = new InheritableThreadLocalSecurityContextHolderStrategy();
		} else if (strategyName.equals(MODE_GLOBAL)) {
			strategy = new GlobalSecurityContextHolderStrategy();
		} else {
			// Try to load a custom strategy
			try {
				@SuppressWarnings("unchecked")
				Class<SecurityContextHolderStrategy> clazz = (Class<SecurityContextHolderStrategy>) Class
						.forName(strategyName);
				Constructor<SecurityContextHolderStrategy> customStrategy = clazz
						.getConstructor(new Class[] {});
				strategy = (SecurityContextHolderStrategy) customStrategy
						.newInstance(new Object[] {});
			} catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
		}

		initializeCount++;
	}

	/**
	 * Associates a new <code>SecurityContext</code> with the current thread of
	 * execution.
	 * 
	 * @param context
	 *            the new <code>SecurityContext</code> (may not be
	 *            <code>null</code>)
	 */
	public static void setContext(SecurityContext context) {
		strategy.setContext(context);
	}

	/**
	 * Changes the preferred strategy. Do <em>NOT</em> call this method more
	 * than once for a given JVM, as it will reinitialize the strategy and
	 * adversely affect any existing threads using the old strategy.
	 * 
	 * @param strategyName
	 *            the fully qualified classname of the strategy that should be
	 *            used.
	 */
	public static void setStrategyName(String strategyName) {
		SecurityContextHolder.strategyName = strategyName;
		initialize();
	}

	public String toString() {
		return "SecurityContextHolder[strategy='" + strategyName + "'; initializeCount="
				+ initializeCount + "]";
	}
}
