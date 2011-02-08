/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.view.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.core.Environment;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class FreemarkerTemplateEngine implements TemplateEngine {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);
	protected FreemarkerManager freemarkerManager;
	protected Configuration config;
	private static final String UI_ENV_CACHE = ".ui.envs";
	private static final String TEMPLATE_MODEL = ".beangle.TemplateModel";

	public void render(String template, ValueStack stack, Writer writer, Component component)
			throws Exception {
		SimpleHash model = buildModel(stack, component);
		Object prevTag = model.get("tag");
		model.put("tag", component);
		Environment env = getEnvironment(template, stack, model, writer);
		env.process();
		if (null != prevTag) {
			model.put("tag", prevTag);
		}
	}

	@Inject
	public void setFreemarkerManager(FreemarkerManager mgr) {
		this.freemarkerManager = mgr;
		if (null != freemarkerManager) config = freemarkerManager.getConfig();
	}

	@SuppressWarnings("unchecked")
	private Environment getEnvironment(String templateName, ValueStack stack, SimpleHash model,
			Writer writer) throws Exception {
		Map<String, Environment> envs = (Map<String, Environment>) stack.getContext().get(
				UI_ENV_CACHE);
		if (null == envs) {
			envs = CollectUtils.newHashMap();
			stack.getContext().put(UI_ENV_CACHE, envs);
		}
		Environment env = envs.get(templateName);
		if (null == env) {
			try {
				Template template = config.getTemplate(templateName);
				env = template.createProcessingEnvironment(model, writer);
				envs.put(templateName, env);
			} catch (ParseException pe) {
				throw pe;
			} catch (IOException e) {
				logger.error("Could not load the FreeMarker template named '{}'", templateName);
				logger.error("The TemplateLoader provided by the FreeMarker Configuration was a: "
						+ config.getTemplateLoader().getClass().getName());
			}
		} else {
			env.setOut(writer);
		}
		return env;
	}

	/**
	 * componentless model(one per request).
	 * 
	 * @param templateContext
	 * @return
	 */
	private SimpleHash buildModel(ValueStack stack, Component component) {
		Map<?, ?> context = stack.getContext();
		HttpServletRequest req = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		// build hash
		SimpleHash model = (SimpleHash) req.getAttribute(TEMPLATE_MODEL);
		if (null == model) {
			model = freemarkerManager.buildTemplateModel(stack, null,
					(ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT), req,
					(HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE),
					config.getObjectWrapper());
			req.setAttribute(TEMPLATE_MODEL, model);
		}
		return model;
	}

	public final String getSuffix() {
		return ".ftl";
	}
}
