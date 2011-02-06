/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Toolbar extends ClosingUIBean {

	public Toolbar(ValueStack stack) {
		super(stack);
	}

	public void evaluateParams() {
		generateIdIfEmpty();
//		if (StringUtils.isEmpty(this.id)) {
//			int nextInt = RANDOM.nextInt();
//			nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
//			this.id = "toolbar_" + String.valueOf(nextInt);
//		}
	}

}
