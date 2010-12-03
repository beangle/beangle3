/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessConfig {

	private static final Logger logger = LoggerFactory.getLogger(AccessConfig.class);

	private Long minDuration;

	private Integer cacheSize;

	private static AccessConfig config;

	public static AccessConfig getInstance() {
		if (config == null) {
			config = buildConfig();
		}
		return config;
	}

	@SuppressWarnings("rawtypes")
	public static synchronized AccessConfig buildConfig() {
		Properties props = new Properties();
		InputStream is = AccessConfig.class.getResourceAsStream("/access.properties");
		if (null == is) {
			is = AccessConfig.class
					.getResourceAsStream("/org/beangle/security/monitor/web/access-default.properties");
		}
		AccessConfig newConfig = null;
		try {
			logger.debug("Loading config...");
			props.load(is);
			newConfig = new AccessConfig();
			for (Iterator iterator = props.keySet().iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				BeanUtils.copyProperty(newConfig, name, props.getProperty(name));
			}
			logger.info(newConfig.toString());
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new RuntimeException(e.getMessage());
		}
		return newConfig;
	}

	public String toString() {
		String toStr = "{minDuration=" + minDuration + ";cacheSize=" + cacheSize + "}";
		return toStr;
	}

	public Long getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Long minDuration) {
		this.minDuration = minDuration;
	}

	public Integer getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(Integer cacheSize) {
		this.cacheSize = cacheSize;
	}

}
