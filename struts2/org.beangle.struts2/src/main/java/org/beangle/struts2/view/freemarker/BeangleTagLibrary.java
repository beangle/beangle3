/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;
import org.beangle.struts2.view.components.BeangleModels;

import com.opensymphony.xwork2.util.ValueStack;

public class BeangleTagLibrary implements TagLibrary {

	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new BeangleModels(stack, req, res);
	}

	@SuppressWarnings("rawtypes")
	public List<Class> getVelocityDirectiveClasses() {
		return null;
	}

}
