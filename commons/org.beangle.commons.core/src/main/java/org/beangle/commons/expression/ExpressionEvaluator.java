/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
