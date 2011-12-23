/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.engine.impl;

import org.beangle.emsapp.rule.Context;
import org.beangle.emsapp.rule.RuleBase;
import org.beangle.emsapp.rule.engine.Agenda;
import org.beangle.emsapp.rule.engine.PatternMatcher;

public class FullPatternMatcher implements PatternMatcher {

	public Agenda buildAgenda(RuleBase base, Context context) {
		return new ListAgenda(base.getRules());
	}

}
