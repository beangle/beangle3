/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.commons.lang.StrUtils;
import org.beangle.struts2.view.freemarker.BeangleModels;

import com.opensymphony.xwork2.util.ValueStack;

public class Div extends ClosingUIBean {

	private String href;

	public Div(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null != this.href) {
			this.href = BeangleModels.render.render(getRequestURI(), this.href);
			generateIdIfEmpty();
			String className = "";
			if (null != parameters.get("class")) {
				className = " " + parameters.get("class").toString();
			}
			parameters.put("class", StrUtils.concat("_ajax_container", className));
		}
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
