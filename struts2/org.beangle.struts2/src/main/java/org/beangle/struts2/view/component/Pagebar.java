/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.commons.collection.page.Page;

import com.opensymphony.xwork2.util.ValueStack;

public class Pagebar extends UIBean {

	private Page<?> page;

	public Pagebar(ValueStack stack) {
		super(stack);
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

}
