/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.beangle.commons.property.PropertyConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlPropertyConfigProvider implements PropertyConfigFactory.Provider {

	protected static final Logger logger = LoggerFactory.getLogger(UrlPropertyConfigProvider.class);

	protected ConfigResource resource;

	public Properties getConfig() {
		try {
			Properties properties = new Properties();
			if (null != resource.getGlobal()) {
				populateConfigItems(properties, resource.getGlobal());
			}
			if (null != resource.getLocations()) {
				for (URL url : resource.getLocals()) {
					populateConfigItems(properties, url);
				}
			}
			if (null != resource.getUser()) {
				populateConfigItems(properties, resource.getUser());
			}
			return properties;
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new RuntimeException(e);
		}
	}

	private void populateConfigItems(Properties properties, URL url) {
		logger.debug("loading {}", url);
		try {
			InputStream is = url.openStream();
			properties.load(is);
			is.close();
		} catch (Exception e) {
			logger.error("populate config error", e);
		}
	}

	public ConfigResource getResource() {
		return resource;
	}

	public void setResource(ConfigResource resource) {
		this.resource = resource;
	}

}
