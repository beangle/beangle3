/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Toolbar extends ClosingUIBean {

	private String title;

	public Toolbar(ValueStack stack) {
		super(stack);
	}

	public void evaluateParams() {
		generateIdIfEmpty();
		if (null != title) {
			title = getText(title);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
