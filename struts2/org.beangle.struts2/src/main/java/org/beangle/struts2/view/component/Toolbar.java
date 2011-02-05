/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Random;

import org.apache.commons.lang.xwork.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Toolbar extends ClosingUIBean {
	final private static transient Random RANDOM = new Random();

	public Toolbar(ValueStack stack) {
		super(stack);
	}

	public void evaluateParams() {
		if (StringUtils.isEmpty(this.id)) {
			int nextInt = RANDOM.nextInt();
			nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
			this.id = "toolbar_" + String.valueOf(nextInt);
		}
	}

}
