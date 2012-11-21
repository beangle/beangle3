/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.expression;

import java.util.Map;

/**
 * 表达式执行器
 * 
 * @author chaostone
 * @version $Id: ExpressionEvaluator.java Mar 5, 2012 12:13:41 AM chaostone $
 * @since 2012-03-05
 */
public interface ExpressionEvaluator {
  /**
   * <p>
   * Eval a expression within context
   * </p>
   * 
   * @param exp a java's expression
   * @param context params.
   * @return evaluate result
   */
  Object eval(String exp, Map<String, ?> context);

  /**
   * <p>
   * Eval a expression within context,Return the given type
   * </p>
   * 
   * @param exp a java's expression
   * @param context params.
   * @param resultType What type of the result
   * @return evaluate result
   */
  <T> T eval(String exp, Map<String, ?> context, Class<T> resultType);

}
