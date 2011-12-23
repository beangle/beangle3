/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.engine;

import org.beangle.emsapp.rule.Context;
import org.beangle.emsapp.rule.RuleBase;

/**
 * 规则匹配器，用于匹配对应数据起作用的规则
 * 
 * @author chaostone
 */
public interface PatternMatcher {

	/**
	 * 根据规则集，判断哪些属于这次的执行范围
	 * 
	 * @param base
	 * @param context
	 * @return
	 */
	public Agenda buildAgenda(RuleBase base, Context context);
}
