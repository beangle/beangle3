/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class ClosingUIBean extends UIBean {

	public ClosingUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	public boolean end(Writer writer, String body) {
		evaluateExtraParams();
		int length = body.length();
		if (length > 0) stack.getContext().put("nested_body", body);
		try {
			mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (length > 0) stack.getContext().remove("nested_body");
		return false;
	}

	@Override
	public boolean usesBody() {
		return true;
	}

}
