/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule.engine.impl;

import org.beangle.rule.Context;
import org.beangle.rule.RuleBase;
import org.beangle.rule.engine.Agenda;
import org.beangle.rule.engine.PatternMatcher;

public class FullPatternMatcher implements PatternMatcher {

	public Agenda buildAgenda(RuleBase base, Context context) {
		return new ListAgenda(base.getRules());
	}

}
