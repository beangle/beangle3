/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.util;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.beangle.commons.script.EvaluationException;
import org.beangle.commons.script.ExpressionEvaluator;

/**
 * 基于OGNL的表达式执行器
 * 
 * @author chaostone
 * @version $Id: OgnlExpressionEvaluator.java Mar 5, 2012 12:13:41 AM chaostone $
 */
public class OgnlEvaluator implements ExpressionEvaluator {

  private Map<String, Object> trees = new java.util.WeakHashMap<String, Object>();

  public void parse(String exp) throws EvaluationException {
    try {
      Object tree = Ognl.parseExpression(exp);
      trees.put(exp, tree);
    } catch (OgnlException e) {
      throw new EvaluationException(e);
    }
  }

  /**
   * <p>
   * Eval a expression within context
   * </p>
   * 
   * @param exp a java's expression
   * @param root params.
   * @return evaluate result
   */
  public Object eval(String exp, Object root) {
    try {
      Object tree = trees.get(exp);
      if (null == tree) {
        tree = Ognl.parseExpression(exp);
        trees.put(exp, tree);
      }
      return Ognl.getValue(tree, root);
    } catch (OgnlException e) {
      throw new EvaluationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T eval(String exp, Object root, Class<T> resultType) {
    try {
      Object tree = trees.get(exp);
      if (null == tree) {
        tree = Ognl.parseExpression(exp);
        trees.put(exp, tree);
      }
      return (T) Ognl.getValue(tree, root, resultType);
    } catch (OgnlException e) {
      throw new EvaluationException(e);
    }
  }

}
