/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.ObjectUtils;
import org.beangle.lang.StrUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Div extends ClosingUIBean {

	private String href;

	private String astarget;

	public Div(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null != href) {
			generateIdIfEmpty();
			href = render(this.href);
		}
		if (!ObjectUtils.equals(astarget, "false")) {
			String className = "";
			if (null != parameters.get("class")) {
				className = " " + parameters.get("class").toString();
			}
			parameters.put("class", StrUtils.concat("_ajax_target", className));
		}
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getAstarget() {
		return astarget;
	}

	public void setAstarget(String astarget) {
		this.astarget = astarget;
	}

}
