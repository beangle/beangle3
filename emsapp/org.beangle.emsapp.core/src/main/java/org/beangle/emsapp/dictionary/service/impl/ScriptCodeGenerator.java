/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.service.impl;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.beangle.emsapp.dictionary.service.CodeFixture;
import org.beangle.emsapp.dictionary.service.CodeGenerator;

import bsh.Interpreter;

/**
 *
 * @author chaostone
 * @version $Id: ScriptCodeGenerator.java May 5, 2011 3:53:15 PM chaostone $
 */

public class ScriptCodeGenerator implements CodeGenerator {

	protected Interpreter interpreter = new Interpreter();

	public void setUp() throws Exception {
	}

	public String gen(CodeFixture fixture) {
		try {
			interpreter.set("entity", fixture.getEntity());
			setUp();
			return (String) interpreter.eval(fixture.getScript());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String test(CodeFixture fixture) {
		try {
			for (Iterator<String> iter = fixture.getParams().keySet().iterator(); iter.hasNext();) {
				String param = iter.next();
				interpreter.set(param, fixture.getParams().get(param));
			}
			if (StringUtils.isNotEmpty(fixture.getScript())) {
				interpreter.eval(fixture.getScript());
			}
			return gen(fixture);
		} catch (Exception e) {
			return ExceptionUtils.getFullStackTrace(e);
		}
	}

	public boolean isValidCode(String code) {
		return (StringUtils.isNotEmpty(code)) && (code.length() <= CodeGenerator.MAX_LENGTH)
				&& !CodeGenerator.MARK.equals(code);
	}

}
