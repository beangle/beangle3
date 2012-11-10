/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component.ComponentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * Adpater new freemarker directive api.
 * but performance is not good.
 * 
 * @author chaostone
 * @since 3.0.1
 */
public abstract class ComponentDirective implements TemplateDirectiveModel {
  private static final Logger logger = LoggerFactory.getLogger(ComponentDirective.class);

  protected ValueStack stack;

  public ComponentDirective(ValueStack stack) {
    this.stack = stack;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
      throws TemplateException, IOException {
    Component bean = getComponent();
    BeansWrapper objectWrapper = BeansWrapper.getDefaultInstance();
    for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
      Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        Method m = ComponentHelper.getWriteMethod(bean, key);
        if (null != m) {
          if (value instanceof TemplateModel) {
            try {
              value = objectWrapper.unwrap((TemplateModel) value);
            } catch (TemplateModelException e) {
              logger.error("failed to unwrap [" + value + "] it will be ignored", e);
            }
          }
          try {
            m.invoke(bean, value);
          } catch (Exception e) {
            logger.error("invoke method " + m.getName() + " with value " + value, e);
          }
        } else {
          bean.getParameters().put(key, value);
        }
      }
    }

    Writer out = env.getOut();
    boolean evaluate = bean.start(out);
    if (evaluate) {
      if (bean.usesBody() && null != body) {
        StringWriter sw = new StringWriter();
        body.render(sw);
        while (bean.end(out, sw.toString())) {
          sw.getBuffer().delete(0, sw.getBuffer().length());
          body.render(sw);
        }
      } else {
        if (evaluate) bean.end(out, "");
      }
    }
  }

  abstract protected Component getComponent();
}
