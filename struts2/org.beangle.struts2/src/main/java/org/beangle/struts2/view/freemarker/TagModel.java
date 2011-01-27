/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

public abstract class TagModel implements TemplateTransformModel {

	private static final Logger logger = LoggerFactory.getLogger(TagModel.class);

	protected ValueStack stack;
	protected HttpServletRequest req;
	protected HttpServletResponse res;

	public TagModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Writer getWriter(Writer writer, Map params) throws TemplateModelException, IOException {
		Component bean = getBean();
		Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
		container.inject(bean);
		BeansWrapper objectWrapper = BeansWrapper.getDefaultInstance();
		for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if (PropertyUtils.isWriteable(bean, key)) {
					if (value instanceof TemplateModel) {
						try {
							value = objectWrapper.unwrap((TemplateModel) value);
						} catch (TemplateModelException e) {
							logger.error("failed to unwrap [" + value + "] it will be ignored", e);
						}
					}
					try {
						PropertyUtils.setProperty(bean, key, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					bean.getParameters().put(key, value);
				}
			}
		}
		// Map unwrappedParameters = unwrapParameters(params);
		// bean.copyParams(unwrappedParameters);
		// bean.getParameters().putAll(unwrappedParameters);
		return new ResetCallbackWriter(bean, writer);
	}

	protected abstract Component getBean();
}
