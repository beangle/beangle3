/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import javax.servlet.http.HttpServletRequest;

/**
 * 从request中提取访问系统的功能点名称。
 * 
 * @author chaostone
 */
public interface ResourceExtractor {

	public String extract(HttpServletRequest request);

	public String extract(String URI);
}
