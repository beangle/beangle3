/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import org.beangle.commons.collection.page.Page;

public class PageChecker {

	public boolean isPage(Object argument) {
		return argument instanceof Page<?>;
	}

}
