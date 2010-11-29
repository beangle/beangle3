/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.struts2.view.freemarker.tags.TextModel;

import com.opensymphony.xwork2.util.ValueStack;

public class BeangleModels {

	protected ValueStack stack;
	protected HttpServletRequest req;
	protected HttpServletResponse res;

	protected TextModel text;

	public BeangleModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	public TextModel getText() {
		if (null == text) {
			text = new TextModel(stack, req, res);
		}
		return text;
	}

	public void setText(TextModel text) {
		this.text = text;
	}

}
