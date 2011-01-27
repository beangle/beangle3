/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class IterableUIBean extends ClosingUIBean {

	public IterableUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected abstract boolean next();

	protected void iterator(Writer writer, String body) {
		stack.getContext().put("nested_body", body);
		try {
			mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		stack.getContext().remove("nested_body");
	}

	@Override
	public boolean start(Writer writer) {
		evaluateExtraParams();
		return next();
	}

	@Override
	public boolean end(Writer writer, String body) {
		iterator(writer, body);
		return next();
	}

}
