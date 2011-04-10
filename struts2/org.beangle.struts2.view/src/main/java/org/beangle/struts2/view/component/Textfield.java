/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Textfield extends UIBean {
	private String name;
	private String title;

	public Textfield(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == title) title = name;
		if (null == this.id) id = name;
		title = getText(title);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
