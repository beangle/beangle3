/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import org.beangle.struts2.view.component.template.TemplateHelper;

import com.opensymphony.xwork2.util.ValueStack;

public class ClosingUIBean extends UIBean {

	public ClosingUIBean(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean start(Writer writer) {
		evaluateExtraParams();
		return true;
	}

	@Override
	public boolean end(Writer writer, String body) {
		int length = body.length();
		if (length > 0) stack.getContext().put("nested_body", body);
		try {
			mergeTemplate(writer, TemplateHelper.buildFullName(getTheme(), getClass()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (length > 0) stack.getContext().remove("nested_body");
		return false;
	}

	@Override
	final public boolean usesBody() {
		return true;
	}
}
