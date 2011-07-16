/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Dialog.java Jul 16, 2011 2:01:14 PM chaostone $
 */
public class Dialog extends ClosingUIBean {

	String title;
	String href;

	String modal = "false";

	public Dialog(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		this.href = render(href);
	}

}
