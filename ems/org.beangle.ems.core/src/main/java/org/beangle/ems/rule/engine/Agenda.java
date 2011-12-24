/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.engine;

import java.util.List;

import org.beangle.ems.rule.Rule;

/**
 * 规则执行议程(顺序)
 * 
 * @author chaostone
 */
public interface Agenda {

	public List<Rule> getRules();

}
