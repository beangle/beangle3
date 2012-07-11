/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

  /** {@inheritDoc} */
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
