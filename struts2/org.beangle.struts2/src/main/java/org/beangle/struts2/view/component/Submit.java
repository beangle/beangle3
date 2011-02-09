/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.commons.lang.StrUtils;
import org.beangle.struts2.view.freemarker.BeangleModels;

import com.opensymphony.xwork2.util.ValueStack;

public class Submit extends UIBean {

	String formId;
	String onsubmit;
	String action;
	String value;

	public Submit(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == formId) {
			Form f = findAncestor(Form.class);
			if (null != f) formId = f.getId();
		}
		if (null != onsubmit && -1 != onsubmit.indexOf('(')) {
			onsubmit = StrUtils.concat("'", onsubmit, "'");
		}
		if (null != value) {
			value = getText(value);
		}
		if (null != action) {
			action = BeangleModels.render.render(getRequestURI(), action);
		}
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getOnsubmit() {
		return onsubmit;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String href) {
		this.action = href;
	}

}
