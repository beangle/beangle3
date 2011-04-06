/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Iframe extends ClosingUIBean {

	String src;

	public Iframe(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		src = render(src);
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
