/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.components.Component;
import org.apache.struts2.components.Form;
import org.apache.struts2.components.template.Template;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.view.components.template.RenderingContext;
import org.beangle.struts2.view.components.template.TemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class UIBean extends Component {

	private static final Logger logger = LoggerFactory.getLogger(UIBean.class);
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public UIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack);
		this.request = request;
		this.response = response;
	}

	// The template to use, overrides the default one.
	protected String template;
	// templateDir and theme attributes
	protected String templateDir;
	protected String theme = "beangle";

	protected String id;

	/**
	 * A contract that requires each concrete UI Tag to specify which template
	 * should be used as a default.
	 * 
	 * @return The name of the template to be used as the default.
	 */
	protected String getDefaultTemplate() {
		return DefaultTemplates.getDefaultTemplate(getClass());
	}

	protected TemplateEngine engine;

	@Inject(StrutsConstants.STRUTS_UI_TEMPLATEDIR)
	public void setTemplateDir(String dir) {
		this.templateDir = dir;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Inject
	public void setEngine(TemplateEngine engine) {
		this.engine = engine;
	}

	public String getTemplateDir() {
		if (null == this.templateDir) {
			String templateDir = null;
			if (this.templateDir != null) {
				templateDir = findString(this.templateDir);
			}
			// If templateDir is not explicitly given,
			// try to find attribute which states the dir set to use
			if ((templateDir == null) || (templateDir.equals(""))) {
				templateDir = stack.findString("#attr.templateDir");
			}
			// Defaults to 'template'
			if ((templateDir == null) || (templateDir.equals(""))) {
				templateDir = "template";
			}
			this.templateDir = templateDir;
		}
		return templateDir;
	}

	public String getTheme() {
		if (null == this.theme) {
			String theme = null;
			if (this.theme != null) {
				theme = findString(this.theme);
			}
			if (theme == null || theme.equals("")) {
				Form form = (Form) findAncestor(Form.class);
				if (form != null) {
					theme = form.getTheme();
				}
			}
			// If theme set is not explicitly given,
			// try to find attribute which states the theme set to use
			if ((theme == null) || (theme.equals(""))) {
				theme = stack.findString("#attr.theme");
			}
			this.theme = theme;
		}
		return theme;
	}

	protected void evaluateExtraParams() {
	}

	@Override
	public boolean end(Writer writer, String body) {
		evaluateExtraParams();
		try {
			mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected Template buildTemplateName(String myTemplate, String myDefaultTemplate) {
		String template = myDefaultTemplate;
		if (myTemplate != null) {
			template = findString(myTemplate);
		}
		String templateDir = getTemplateDir();
		String theme = getTheme();
		return new Template(templateDir, theme, template);
	}

	protected void mergeTemplate(Writer writer, Template template) throws Exception {
		logger.debug("Rendering template {}", template);
		engine.renderTemplate(new RenderingContext(template, writer, getStack(), getParameters(),
				this));
	}

	private static class DefaultTemplates {

		public static Map<Class<?>, String> defaultNames = CollectUtils.newHashMap();

		public static String getDefaultTemplate(Class<?> clazz) {
			String name = defaultNames.get(clazz);
			if (null == name) {
				name = StringUtils.uncapitalize(clazz.getSimpleName());
				defaultNames.put(clazz, name);
			}
			return name;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
