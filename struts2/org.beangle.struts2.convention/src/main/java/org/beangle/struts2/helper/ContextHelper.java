/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.helper;

import com.opensymphony.xwork2.ActionContext;

public class ContextHelper {

  public static void put(String key, Object value) {
    ActionContext.getContext().getContextMap().put(key, value);
  }

}
