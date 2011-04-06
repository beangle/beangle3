/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Os implements Serializable, Comparable<Os> {

	private static final long serialVersionUID = -7506270303767154240L;

	private static Logger logger = LoggerFactory.getLogger(Os.class);
	public static Map<String, Os> osMap = CollectUtils.newHashMap();
	public static final Os UNKNOWN = new Os(OsCategory.UNKNOWN, null);

	public final OsCategory category;
	public final String version;

	private Os(OsCategory category, String version) {
		super();
		this.category = category;
		this.version = version;
	}

	@Override
	public String toString() {
		return category.getName() + " " + (version == null ? "" : version);
	}

	/**
	 * Parses user agent string and returns the best match. Returns Os.UNKNOWN
	 * if there is no match.
	 * 
	 * @param agentString
	 * @return Os
	 */
	public static Os parse(String agentString) {
		if (StringUtils.isEmpty(agentString)) { return Os.UNKNOWN; }
		for (OsCategory category : OsCategory.values()) {
			String version = category.match(agentString);
			if (version != null) {
				String key = category.getName() + "/" + version;
				Os os = osMap.get(key);
				if (null == os) {
					os = new Os(category, version);
					osMap.put(key, os);
				}
				return os;
			}
		}
		logger.debug("unknown browser: {}", agentString);
		return Os.UNKNOWN;
	}

	public int compareTo(Os o) {
		return category.compareTo(o.category);
	}

}
