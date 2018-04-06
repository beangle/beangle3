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
package org.beangle.ems.rule.impl;

import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.engine.RuleExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutor2 implements RuleExecutor {
  private static final Logger logger = LoggerFactory.getLogger(RuleExecutor2.class);

  public boolean execute(Context context) {
    logger.info("I am rule executor No 2");
    return true;
  }

}
