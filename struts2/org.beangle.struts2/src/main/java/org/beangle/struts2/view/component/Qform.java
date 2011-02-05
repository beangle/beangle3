/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Qform extends Form {

	private String title;

	public Qform(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		title = getText(title);
		super.evaluateParams();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
