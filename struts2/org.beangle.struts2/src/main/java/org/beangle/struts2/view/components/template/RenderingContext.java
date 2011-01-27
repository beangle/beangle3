/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components.template;

import java.io.Writer;
import java.util.Map;

import org.apache.struts2.components.Component;
import org.apache.struts2.components.template.Template;
import org.apache.struts2.components.template.TemplateRenderingContext;

import com.opensymphony.xwork2.util.ValueStack;

public class RenderingContext extends TemplateRenderingContext {

	Component component;

	public RenderingContext(Template template, Writer writer, ValueStack stack, Map<?, ?> params,
			Component tag) {
		super(template, writer, stack, params, null);
		this.component = tag;
	}

	public Component getComponent() {
		return component;
	}

}
