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
   * eval.
   * </p>
   * 
   * @param exp a {@link java.lang.String} object.
   * @param context a {@link java.util.Map} object.
   * @param resultType a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a T object.
   */
  <T> T eval(String exp, Map<String, ?> context, Class<T> resultType);

  /**
   * <p>
   * eval.
   * </p>
   * 
   * @param exp a {@link java.lang.String} object.
   * @param context a {@link java.util.Map} object.
   * @return a {@link java.lang.Object} object.
   */
  Object eval(String exp, Map<String, ?> context);
}
