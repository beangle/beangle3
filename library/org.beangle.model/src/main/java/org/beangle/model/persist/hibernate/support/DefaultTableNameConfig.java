/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.spring.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据报名动态设置schema,prefix名字
 * 
 * @author chaostone
 */
public class DefaultTableNameConfig implements TableNameConfig {

	private static final Logger logger = LoggerFactory.getLogger(DefaultTableNameConfig.class);

	private final List<TableNamePattern> patterns = new ArrayList<TableNamePattern>();

	private final Map<String, TableNamePattern> packagePatterns = CollectUtils.newHashMap();

	private ConfigResource resource;

	public void addConfig(URL url) {
		loadProperties(url);
		Collections.sort(patterns);
		for (TableNamePattern pattern : patterns) {
			logger.info("table name pattern {}", pattern);
		}
	}

	private void loadProperties(URL url) {
		try {
			logger.debug("loading {}", url);
			InputStream is = url.openStream();
			Properties props = new Properties();
			if (null != is) {
				props.load(is);
			}
			for (Iterator<Object> iter = props.keySet().iterator(); iter.hasNext();) {
				String packageName = (String) iter.next();
				String schemaPrefix = props.getProperty(packageName).trim();

				String schema = null;
				String prefix = null;
				int commaIndex = schemaPrefix.indexOf(',');
				if (commaIndex < 0 || (commaIndex + 1 == schemaPrefix.length())) {
					schema = schemaPrefix;
				} else if (commaIndex == 0) {
					prefix = schemaPrefix.substring(1);
				} else {
					schema = StringUtils.substringBefore(schemaPrefix, ",");
					prefix = StringUtils.substringAfter(schemaPrefix, ",");
				}
				TableNamePattern pattern = (TableNamePattern) packagePatterns.get(packageName);
				if (null == pattern) {
					pattern = new TableNamePattern(packageName, schema, prefix);
					packagePatterns.put(packageName, pattern);
					patterns.add(pattern);
				} else {
					pattern.setSchema(schema);
					pattern.setPrefix(prefix);
				}
			}
			is.close();
		} catch (IOException e) {
			logger.error("property load error", e);
		}
	}

	public String getSchema(String packageName) {
		String schemaName = null;
		for (TableNamePattern packageSchema : patterns) {
			if (packageName.indexOf(packageSchema.getPackageName()) == 0) {
				schemaName = packageSchema.getSchema();
			}
		}
		return schemaName;
	}

	public String getPrefix(String packageName) {
		String prefix = null;
		for (TableNamePattern packageSchema : patterns) {
			if (packageName.indexOf(packageSchema.getPackageName()) == 0) {
				prefix = packageSchema.getPrefix();
			}
		}
		return prefix;
	}

	public List<TableNamePattern> getPatterns() {
		return patterns;
	}

	public ConfigResource getResource() {
		return resource;
	}

	public void setResource(ConfigResource resource) {
		this.resource = resource;
		if (null != resource) {
			for (final URL url : resource.getAllPaths()) {
				addConfig(url);
			}
		}
	}

}

/**
 * 表命名模式
 * 
 * @author chaostone
 */
class TableNamePattern implements Comparable<TableNamePattern> {
	// 报名
	String packageName;
	// 模式名
	String schema;
	// 前缀名
	String prefix;

	public TableNamePattern(String packageName, String schemaName, String prefix) {
		this.packageName = packageName;
		this.schema = schemaName;
		this.prefix = prefix;
	}

	public int compareTo(TableNamePattern other) {
		return this.packageName.compareTo(other.packageName);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schemaName) {
		this.schema = schemaName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[package:").append(packageName).append(", schema:").append(schema);
		sb.append(", prefix:").append(prefix).append(']');
		return sb.toString();
	}

}
