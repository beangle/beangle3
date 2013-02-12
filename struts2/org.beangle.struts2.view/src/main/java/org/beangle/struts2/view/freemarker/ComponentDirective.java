/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.view.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.beangle.commons.lang.annotation.Beta;
import org.beangle.commons.lang.asm.Mirrors;
import org.beangle.struts2.view.component.Component;
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
@Beta
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
        if (Mirrors.hasProperty(bean, key)) {
          if (value instanceof TemplateModel) {
            try {
              value = objectWrapper.unwrap((TemplateModel) value);
            } catch (TemplateModelException e) {
              logger.error("failed to unwrap [" + value + "] it will be ignored", e);
            }
          }
          try {
            Mirrors.setProperty(bean, key, value);
          } catch (Exception e) {
            logger.error("invoke set property [" + key + "] with value " + value, e);
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
