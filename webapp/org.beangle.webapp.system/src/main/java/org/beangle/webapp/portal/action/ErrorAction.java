/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.portal.action;

import org.beangle.struts2.action.BaseAction;

/**
 * @author dell
 */
public class ErrorAction extends BaseAction {

	public String index() {
		if (get("errorCode") != null) {
			addActionError(get("errorCode"));
		}
		return ERROR;
	}

}
