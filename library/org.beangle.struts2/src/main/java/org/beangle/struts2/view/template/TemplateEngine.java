/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.io.Writer;

import org.beangle.struts2.view.component.Component;

import com.opensymphony.xwork2.util.ValueStack;

public interface TemplateEngine {

	public void render(String template, ValueStack stack, Writer writer, Component component)
			throws Exception;

	public String getSuffix();
}
