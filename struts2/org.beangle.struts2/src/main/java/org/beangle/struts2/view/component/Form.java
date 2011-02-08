/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.struts2.view.freemarker.BeangleModels;

import com.opensymphony.xwork2.util.ValueStack;

public class Form extends ClosingUIBean {

	protected String name;
	protected String action;
	protected String target;

	public Form(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == name && null == id) {
			generateIdIfEmpty();
			name=id;
		} else if (null == id) {
			id = name;
		}
		action = BeangleModels.render.render(getRequestURI(), action);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
