/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class TemplateEngine {
	private static final Logger logger = LoggerFactory.getLogger(TemplateEngine.class);
	protected FreemarkerManager freemarkerManager;
	protected Configuration config;
	private static final String UI_ENV_CACHE = ".ui.envs";

	@Inject
	public void setFreemarkerManager(FreemarkerManager mgr) {
		this.freemarkerManager = mgr;
	}

	private Environment getEnvironment(TemplateRenderingContext templateContext, SimpleHash model,
			Writer writer) throws Exception {
		HttpServletRequest req = (HttpServletRequest) templateContext.getStack().getContext()
				.get(ServletActionContext.HTTP_REQUEST);
		// prepare freemarker envrionmemt
		Map<String, Environment> envs = (Map<String, Environment>) req.getAttribute(UI_ENV_CACHE);
		if (null == envs) {
			envs = CollectUtils.newHashMap();
			req.setAttribute(UI_ENV_CACHE, envs);
		}
		String templateName = templateContext.getTemplate();
		Environment env = envs.get(templateName);
		if (null == env) {
			try {
				Template template = config.getTemplate(templateName + getSuffix());
				env = template.createProcessingEnvironment(model, writer);
				envs.put(templateName, env);
			} catch (IOException e) {
				logger.error("Could not load the FreeMarker template named '{}'", templateName);
				logger.error("The TemplateLoader provided by the FreeMarker Configuration was a: "
						+ config.getTemplateLoader().getClass().getName());
			}
		}
		env.setOut(writer);
		return env;
	}

	/**
	 * componentless model(one per request).
	 * 
	 * @param templateContext
	 * @return
	 */
	private SimpleHash buildModel(TemplateRenderingContext templateContext) {
		ValueStack stack = templateContext.getStack();
		Map<?, ?> context = stack.getContext();
		HttpServletRequest req = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		ServletContext servletContext = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		if (null == config) config = freemarkerManager.getConfiguration(servletContext);
		// build hash
		SimpleHash model = (SimpleHash) req.getAttribute(FreemarkerManager.ATTR_TEMPLATE_MODEL);
		if (null == model) {
			model = freemarkerManager.buildTemplateModel(stack, null, servletContext, req,
					(HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE),
					config.getObjectWrapper());
		}
		model.put("tag", templateContext.getComponent());
		return model;
	}

	public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
		SimpleHash model = buildModel(templateContext);
		// getTemplate(templateContext).process(model, writer);
		getEnvironment(templateContext, model, templateContext.getWriter()).process();
	}

	protected String getSuffix() {
		return ".ftl";
	}

}
