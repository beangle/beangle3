/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component.template;

import java.io.Writer;

import org.beangle.struts2.view.component.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class TemplateRenderingContext {

	final String template;
	final Writer writer;
	final ValueStack stack;
	final Component component;

	public TemplateRenderingContext(String template, Writer writer, ValueStack stack,
			Component component) {
		super();
		this.template = template;
		this.writer = writer;
		this.stack = stack;
		this.component = component;
	}

	public String getTemplate() {
		return template;
	}

	public Writer getWriter() {
		return writer;
	}

	public ValueStack getStack() {
		return stack;
	}

	public Component getComponent() {
		return component;
	}

}
