/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.config;

/**
 * <p>
 * This interface defines how the action configurations for the current web application can be
 * constructed. This must find all actions that are not specifically defined in the struts XML files
 * or any plugins. Furthermore, it must make every effort to locate all action results as well.
 * </p>
 */
public interface ActionConfigBuilder {
	/**
	 * Builds all the action configurations and stores them into the XWork
	 * configuration instance via XWork dependency injetion.
	 */
	void buildActionConfigs();

	boolean needsReload();

	void destroy();
}
