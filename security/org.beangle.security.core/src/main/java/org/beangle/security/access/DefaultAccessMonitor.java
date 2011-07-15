/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.access;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * 缺省的访问监控器
 * 
 * @author chaostone
 * @version $Id: DefaultAccessMonitor.java Jul 8, 2011 7:59:59 PM chaostone $
 */
public class DefaultAccessMonitor implements AccessMonitor {

	Map<String, String> resources = CollectUtils.newConcurrentHashMap();

	public void access(String sessionId, String resource) {
		resources.put(sessionId, resource);
	}

	public String getResource(String sessionId) {
		return resources.get(sessionId);
	}

}
