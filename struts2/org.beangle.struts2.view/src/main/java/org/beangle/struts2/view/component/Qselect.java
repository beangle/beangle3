/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Qselect extends Select {

	private String title;

	public Qselect(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		super.evaluateParams();
		title = (null == title) ? getText(name, null) : getText(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
