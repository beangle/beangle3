/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Password.java May 3, 2011 2:18:50 PM chaostone $
 */
public class Password extends AbstractTextBean {

	public Password(ValueStack stack) {
		super(stack);
		maxlength = "10";
	}

}
