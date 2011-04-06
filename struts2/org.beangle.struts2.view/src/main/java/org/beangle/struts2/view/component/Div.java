/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.beangle.commons.lang.StrUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Div extends ClosingUIBean {

	private String href;

	private String asContainer;

	public Div(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null != href) {
			generateIdIfEmpty();
			href = render(this.href);
		}
		if (!ObjectUtils.equals(asContainer, "false")) {
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

	public String getAsContainer() {
		return asContainer;
	}

	public void setAsContainer(String asContainer) {
		this.asContainer = asContainer;
	}

}
