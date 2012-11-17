/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.bean;

/**
 * Url render
 * 
 * @author chaostone
 * @since 2.4
 */
public interface ActionUrlRender {

  String render(String referer, String uri);
}
