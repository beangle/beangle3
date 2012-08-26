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

/**
 * Freemarker Template Engin
 * <ul>
 * <li>Cache freemaker envionment in context</li>
 * <li>User hashmodel store in request</li>
 * <li>Load hierarchical templates</li>
 * <li>Disabled freemarker localized lookup in template loading</li>
 * </ul>
 * 
 * @author chaostone
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {
  private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);
  private static final String UI_ENV_CACHE = ".ui.envs";

  protected FreemarkerManager freemarkerManager;
  protected Configuration config;

  public void render(String template, ValueStack stack, Writer writer, Component component) throws Exception {
    SimpleHash model = buildModel(stack, component);
    Object prevTag = model.get("tag");
    model.put("tag", component);
    Environment env = getEnvironment(template, stack, model, writer);
    env.process();
    if (null != prevTag) model.put("tag", prevTag);
  }

  @Inject
  public void setFreemarkerManager(FreemarkerManager mgr) {
    this.freemarkerManager = mgr;
    // Disable freemarker localized lookup
    if (null != freemarkerManager) {
      config = (Configuration) freemarkerManager.getConfig().clone();
      config.setLocalizedLookup(false);
    }
  }

  /**
   * Load template in hierarchical path
   * 
   * @param templateName
   * @return
   * @throws Exception
   */
  private Template getTemplate(String templateName) throws Exception {
    Template template = null;
    String curTemplate = templateName;
    while (null == template) {
      try {
        template = config.getTemplate(curTemplate);
      } catch (ParseException e) {
        throw e;
      } catch (IOException e) {
        curTemplate = getParentTemplate(curTemplate);
        if (null == curTemplate) {
          logger.error("Could not load template named '{}',TemplateLoader is {}", templateName, config
              .getTemplateLoader().getClass());
          throw e;
        }
      }
    }
    return template;
  }

  /**
   * Generator Envionment from template or Get it from stack.context
   */
  @SuppressWarnings("unchecked")
  private Environment getEnvironment(String templateName, ValueStack stack, SimpleHash model, Writer writer)
      throws Exception {
    Map<String, Environment> envs = (Map<String, Environment>) stack.getContext().get(UI_ENV_CACHE);
    if (null == envs) {
      envs = CollectUtils.newHashMap();
      stack.getContext().put(UI_ENV_CACHE, envs);
    }
    Environment env = envs.get(templateName);
    if (null == env) {
      try {
        Template template = getTemplate(templateName);
        env = template.createProcessingEnvironment(model, writer);
        envs.put(templateName, env);
      } catch (ParseException pe) {
        throw pe;
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
    HttpServletRequest req = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
    // build hash
    SimpleHash model = (SimpleHash) req.getAttribute(FreemarkerManager.ATTR_TEMPLATE_MODEL);
    if (null == model) {
      model = freemarkerManager.buildTemplateModel(stack, null,
          (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT), req,
          (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE), config.getObjectWrapper());
      req.setAttribute(FreemarkerManager.ATTR_TEMPLATE_MODEL, model);
    }
    return model;
  }

  public final String getSuffix() {
    return ".ftl";
  }
}
