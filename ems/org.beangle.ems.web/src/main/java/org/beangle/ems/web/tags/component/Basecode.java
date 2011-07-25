/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.tags.component;

import org.beangle.struts2.view.component.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Basecode.java Jul 25, 2011 3:14:59 PM chaostone $
 */
public class Basecode extends UIBean {

	private String name;

	public Basecode(ValueStack stack) {
		super(stack);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
