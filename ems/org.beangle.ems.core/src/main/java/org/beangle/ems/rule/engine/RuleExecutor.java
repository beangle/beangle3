/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.engine;

import org.beangle.ems.rule.Context;


/**
 * 规则执行者
 * 
 * @author chaostone
 */
public interface RuleExecutor {

	public boolean execute(Context context);
}
