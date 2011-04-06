/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.impl;

import org.beangle.rule.Context;
import org.beangle.rule.engine.RuleExecutor;

public class RuleExecutor2 implements RuleExecutor {

	public boolean execute(Context context) {
		System.out.println("I am rule executor No 2");
		return true;
	}

}
