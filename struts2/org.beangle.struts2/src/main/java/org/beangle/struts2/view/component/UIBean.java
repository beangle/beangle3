/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import org.beangle.struts2.view.component.template.TemplateEngine;
import org.beangle.struts2.view.component.template.TemplateHelper;
import org.beangle.struts2.view.component.template.TemplateRenderingContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 */
public abstract class UIBean extends Component {
	protected String theme = "beangle";
	// protected TemplateEngine engine;
	protected String id;

	public UIBean(ValueStack stack) {
		super(stack);
	}

	// @Inject
	// public void setEngine(TemplateEngine engine) {
	// this.engine = engine;
	// }

	protected void evaluateExtraParams() {
	}

	@Override
	public boolean end(Writer writer, String body) {
		evaluateExtraParams();
		try {
			mergeTemplate(writer, TemplateHelper.buildFullName(getTheme(), getClass()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void mergeTemplate(Writer writer, String template) throws Exception {
		Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
		TemplateEngine engine = container.getInstance(TemplateEngine.class);
		engine.renderTemplate(new TemplateRenderingContext(template, writer, stack, this));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTheme() {
		return theme;
	}
}
