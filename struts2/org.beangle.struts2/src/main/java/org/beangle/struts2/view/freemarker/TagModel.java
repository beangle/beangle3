/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component.ComponentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

public abstract class TagModel implements TemplateTransformModel {

	private static final Logger logger = LoggerFactory.getLogger(TagModel.class);

	protected ValueStack stack;

	public TagModel(ValueStack stack){
		this.stack=stack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Writer getWriter(Writer writer, Map params) throws TemplateModelException, IOException {
		Component bean = getBean();
//		Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
//		container.inject(bean);
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
						e.printStackTrace();
					}
				} else {
					bean.getParameters().put(key, value);
				}
			}
		}
		return new ResetCallbackWriter(bean, writer);
	}

	protected abstract Component getBean();
}
