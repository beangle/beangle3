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
package org.beangle.commons.script;

/**
 * 表达式执行器
 *
 * @author chaostone
 * @version $Id: ExpressionEvaluator.java Mar 5, 2012 12:13:41 AM chaostone $
 * @since 2012-03-05
 */
public interface ExpressionEvaluator {
  /**
   * Parse the expression
   * @param exp
   * @throws EvaluationException
   */
  void parse(String exp) throws EvaluationException;
  /**
   * <p>
   * Eval a expression within context
   * </p>
   *
   * @param exp a java's expression
   * @param root params.
   * @return evaluate result
   */
  Object eval(String exp, Object root);

  /**
   * <p>
   * Eval a expression within context,Return the given type
   * </p>
   *
   * @param exp a java's expression
   * @param root params.
   * @param resultType What type of the result
   * @return evaluate result
   */
  <T> T eval(String exp, Object root, Class<T> resultType);

}
