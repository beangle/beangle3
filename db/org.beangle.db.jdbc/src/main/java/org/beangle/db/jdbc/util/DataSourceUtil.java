/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.jdbc.ds.PoolingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceUtil {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);

	public static DataSource getDataSource(String datasourceName) {
		final Properties props = new Properties();
		try {
			InputStream is = DataSourceUtil.class.getResourceAsStream("/database.properties");
			if (null == is) { throw new RuntimeException("cannot find database.properties"); }
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("cannot find database.properties");
		}
		Enumeration<?> names = props.propertyNames();
		Map<String, String> sourceProps = CollectUtils.newHashMap();
		while (names.hasMoreElements()) {
			String propertyName = (String) names.nextElement();
			if (propertyName.startsWith(datasourceName + ".")) {
				sourceProps.put(StringUtils.substringAfter(propertyName, datasourceName + "."),
						props.getProperty(propertyName));
			}
		}
		if (sourceProps.isEmpty()) {
			return null;
		} else {
			return build(sourceProps);
		}
	}

	public static DataSource build(Map<String, String> properties) {
		return new PoolingDataSourceFactory(properties.get("driverClassName"), properties.get("url"),
				properties.get("username"), properties.get("password")).getObject();
	}

	public static List<String> getDataSourceNames() throws Exception {
		Properties props = new Properties();
		InputStream is = DataSourceUtil.class.getResourceAsStream("/database.properties");
		if (null != is) {
			props.load(is);
		} else {
			throw new RuntimeException("cannot find database.properties");
		}
		Set<String> dialects = CollectUtils.newHashSet();
		Enumeration<?> names = props.propertyNames();
		while (names.hasMoreElements()) {
			String propertyName = (String) names.nextElement();
			String dialect = StringUtils.substringBefore(propertyName, ".");
			if (!dialects.contains(dialect)) {
				dialects.add(dialect);
			}
		}
		return CollectUtils.newArrayList(dialects);
	}

}
