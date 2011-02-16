/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.access.AuthorityManager;
import org.beangle.struts2.view.AbstractTagLibrary;
import org.beangle.struts2.view.TagModel;
import org.beangle.struts2.view.component.Component;
import org.beangle.webapp.security.tag.component.Guard;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

public class SecurityTagLibrary extends AbstractTagLibrary {
	AuthorityManager authorityManager;

	public SecurityTagLibrary() {
		super();
	}

	public SecurityTagLibrary(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		Container container = (Container) stack.getContext().get(ActionContext.CONTAINER);
		ObjectFactory objectFactory = container.getInstance(ObjectFactory.class);
		try {
			authorityManager = (AuthorityManager) objectFactory.buildBean("authorityManager", null, false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SecurityTagLibrary library = new SecurityTagLibrary(stack, req, res);
		library.authorityManager = authorityManager;
		return library;
	}

	public TagModel getGuard() {
		TagModel model = models.get(Guard.class);
		if (null == model) {
			model = new TagModel(stack) {
				protected Component getBean() {
					return new Guard(stack, authorityManager);
				}
			};
			models.put(Guard.class, model);
		}
		return model;
	}

}
