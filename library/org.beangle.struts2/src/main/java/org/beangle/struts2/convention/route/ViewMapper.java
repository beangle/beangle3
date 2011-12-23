/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

/**
 * viewname -> 页面路径的映射
 * 
 * @author chaostone
 */
public interface ViewMapper {

	String getViewPath(String className, String methodName, String viewName);
}
