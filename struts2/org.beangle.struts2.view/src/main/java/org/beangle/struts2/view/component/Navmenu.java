/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Navmenu.java Apr 20, 2011 8:47:00 AM chaostone $
 */
public class Navmenu extends ClosingUIBean {

	String title;

	public Navmenu(ValueStack stack) {
		super(stack);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
