/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;

import org.beangle.commons.lang.asm.Mirrors;
import org.beangle.struts2.view.ResetCallbackWriter;
import org.beangle.struts2.view.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

/**
 * @author chaostone
 * @since 2.0
 */
public class TagModel implements TemplateTransformModel {
  private static final Logger logger = LoggerFactory.getLogger(TagModel.class);

  private Constructor<? extends Component> componentCon;
  private ValueStack stack;

  public TagModel(ValueStack stack) {
  }

  public TagModel(ValueStack stack, Class<? extends Component> clazz) {
    this.stack = stack;
    try {
      componentCon = clazz.getConstructor(ValueStack.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Writer getWriter(Writer writer, Map params) throws TemplateModelException, IOException {
    Component bean = getBean();
    BeansWrapper objectWrapper = BeansWrapper.getDefaultInstance();

    for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
      Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        if (Mirrors.isWriteable(bean, key)) {
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
    return new ResetCallbackWriter(bean, writer);
  }

  protected Component getBean() {
    try {
      return componentCon.newInstance(stack);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
