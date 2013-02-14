/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
