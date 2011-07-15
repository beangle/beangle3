/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 *
 * @author chaostone
 * @version $Id: Emailfield.java Jul 15, 2011 11:13:53 AM chaostone $
 */
public class Emailfield extends AbstractTextBean {

	public Emailfield(ValueStack stack) {
		super(stack);
		check="match('email')";
	}
}
