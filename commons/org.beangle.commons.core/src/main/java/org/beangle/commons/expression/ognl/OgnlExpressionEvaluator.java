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
package org.beangle.commons.expression.ognl;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.beangle.commons.expression.EvaluationException;
import org.beangle.commons.expression.ExpressionEvaluator;

/**
 * 基于OGNL的表达式执行器
 * 
 * @author chaostone
 * @version $Id: OgnlExpressionEvaluator.java Mar 5, 2012 12:13:41 AM chaostone $
 */
public class OgnlExpressionEvaluator implements ExpressionEvaluator {

  private Map<String, Object> trees = new java.util.WeakHashMap<String, Object>();

  /**
   * <p>
   * Eval a expression within context
   * </p>
   * 
   * @param exp a java's expression
   * @param context params.
   * @return evaluate result
   */
  public Object eval(String exp, Map<String, ?> context) {
    try {
      Object tree = trees.get(exp);
      if (null == tree) {
        tree = Ognl.parseExpression(exp);
        trees.put(exp, tree);
      }
      return Ognl.getValue(tree, context);
    } catch (OgnlException e) {
      throw new EvaluationException(e);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public <T> T eval(String exp, Map<String, ?> context, Class<T> resultType) {
    try {
      Object tree = trees.get(exp);
      if (null == tree) {
        tree = Ognl.parseExpression(exp);
        trees.put(exp, tree);
      }
      return (T) Ognl.getValue(tree, (Object) context, resultType);
    } catch (OgnlException e) {
      throw new EvaluationException(e);
    }
  }

}
