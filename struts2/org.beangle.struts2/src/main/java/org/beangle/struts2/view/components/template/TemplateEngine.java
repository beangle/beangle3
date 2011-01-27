/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components.template;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.template.BaseTemplateEngine;
import org.apache.struts2.components.template.FreemarkerTemplateEngine;
import org.apache.struts2.components.template.Template;
import org.apache.struts2.components.template.TemplateRenderingContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;

public class TemplateEngine extends BaseTemplateEngine {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);
	protected FreemarkerManager freemarkerManager;

	@Inject
	public void setFreemarkerManager(FreemarkerManager mgr) {
		this.freemarkerManager = mgr;
	}

	public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
		RenderingContext renderingcontext = (RenderingContext) templateContext;
		// get the various items required from the stack
		ValueStack stack = templateContext.getStack();
		Map<?, ?> context = stack.getContext();
		ServletContext servletContext = (ServletContext) context
				.get(ServletActionContext.SERVLET_CONTEXT);
		HttpServletRequest req = (HttpServletRequest) context
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse res = (HttpServletResponse) context
				.get(ServletActionContext.HTTP_RESPONSE);

		// prepare freemarker
		Configuration config = freemarkerManager.getConfiguration(servletContext);

		// get the list of templates we can use
		List<Template> templates = templateContext.getTemplate().getPossibleTemplates(this);

		// find the right template
		freemarker.template.Template template = null;
		String templateName = null;
		Exception exception = null;
		for (Template t : templates) {
			templateName = getFinalTemplateName(t);
			try {
				// try to load, and if it works, stop at the first one
				template = config.getTemplate(templateName);
				break;
			} catch (ParseException e) {
				// template was found but was invalid - always report this.
				exception = e;
				break;
			} catch (IOException e) {
				// FileNotFoundException is anticipated - report the first
				// IOException if no template found
				if (exception == null) {
					exception = e;
				}
			}
		}

		if (template == null) {
			if (logger.isErrorEnabled()) {
				logger.error("Could not load the FreeMarker template named '"
						+ templateContext.getTemplate().getName() + "':");
				for (Template t : templates) {
					logger.error("Attempted: " + getFinalTemplateName(t));
				}
				logger.error("The TemplateLoader provided by the FreeMarker Configuration was a: "
						+ config.getTemplateLoader().getClass().getName());
			}
			if (exception != null) {
				throw exception;
			} else {
				return;
			}
		}

		logger.debug("Rendering template {}", templateName);

		ActionInvocation ai = ActionContext.getContext().getActionInvocation();

		Object action = (ai == null) ? null : ai.getAction();
		SimpleHash model = freemarkerManager.buildTemplateModel(stack, action, servletContext, req,
				res, config.getObjectWrapper());

		model.put("tag", renderingcontext.getComponent());
		//model.put("themeProperties", getThemeProps(templateContext.getTemplate()));

		// the BodyContent JSP writer doesn't like it when FM flushes
		// automatically --
		// so let's just not do it (it will be flushed eventually anyway)
		Writer writer = templateContext.getWriter();
		final Writer wrapped = writer;
		writer = new Writer() {
			public void write(char cbuf[], int off, int len) throws IOException {
				wrapped.write(cbuf, off, len);
			}

			public void flush() throws IOException {
				// nothing!
			}

			public void close() throws IOException {
				wrapped.close();
			}
		};

		try {
			stack.push(renderingcontext.getComponent());
			template.process(model, writer);
		} finally {
			stack.pop();
		}
	}

	protected String getSuffix() {
		return "ftl";
	}

}
