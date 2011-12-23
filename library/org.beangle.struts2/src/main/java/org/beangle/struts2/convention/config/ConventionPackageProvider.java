/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.config;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.PackageProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

/**
 * <p>
 * This class is a configuration provider for the XWork configuration system. This is really the
 * only way to truly handle loading of the packages, actions and results correctly. This doesn't
 * contain any logic and instead delegates to the configured instance of the
 * {@link ActionConfigBuilder} interface.
 * </p>
 */
public class ConventionPackageProvider implements PackageProvider {
	private ActionConfigBuilder actionConfigBuilder;

	@Inject
	public ConventionPackageProvider(Container container) {
		this.actionConfigBuilder = container.getInstance(ActionConfigBuilder.class, "beangle");
	}

	public void init(Configuration configuration) throws ConfigurationException {
	}

	public boolean needsReload() {
		return actionConfigBuilder.needsReload();
	}

	public void loadPackages() throws ConfigurationException {
		actionConfigBuilder.buildActionConfigs();
	}
}
