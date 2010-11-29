/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;
import org.beangle.security.AuthorityManager;
import org.beangle.webapp.security.tag.freemarker.SecurityModels;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

public class SecurityTagLibrary implements TagLibrary {

	@Inject
	private ObjectFactory objectFactory;

	private AuthorityManager authorityManager;

	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		if (null == authorityManager) {
			try {
				authorityManager = (AuthorityManager) objectFactory.buildBean("authorityManager",
						null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		stack.set("authorityManager", authorityManager);
		return new SecurityModels(stack, req, res);
	}

	@SuppressWarnings("rawtypes")
	public List<Class> getVelocityDirectiveClasses() {
		return null;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

}
