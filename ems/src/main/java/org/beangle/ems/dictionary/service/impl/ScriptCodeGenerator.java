/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.dictionary.service.impl;

import java.util.Iterator;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;

import bsh.Interpreter;

/**
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
      if (Strings.isNotEmpty(fixture.getScript())) {
        interpreter.eval(fixture.getScript());
      }
      return gen(fixture);
    } catch (Exception e) {
      return Throwables.getStackTrace(e);
    }
  }

  public boolean isValidCode(String code) {
    return (Strings.isNotEmpty(code)) && (code.length() <= CodeGenerator.MAX_LENGTH)
        && !CodeGenerator.MARK.equals(code);
  }

}
