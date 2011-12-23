/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class IterableUIBean extends ClosingUIBean {

	public IterableUIBean(ValueStack stack) {
		super(stack);
	}

	protected abstract boolean next();

	protected void iterator(Writer writer, String body) {
		this.body = body;
		try {
			mergeTemplate(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean start(Writer writer) {
		evaluateParams();
		return next();
	}

	@Override
	public boolean doEnd(Writer writer, String body) {
		iterator(writer, body);
		return next();
	}

}
