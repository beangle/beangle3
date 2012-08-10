/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.util;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.predicates.NotEmptyStringPredicate;
import org.beangle.commons.collection.predicates.NotZeroNumberPredicate;

/**
 * 判断实体类中的主键是否是有效主键
 * 
 * @author chaostone
 */
public class ValidEntityKeyPredicate implements Predicate {

  /**
   * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
   */
  public boolean evaluate(Object value) {
    return NotEmptyStringPredicate.INSTANCE.evaluate(value)
        || NotZeroNumberPredicate.INSTANCE.evaluate(value);
  }

  public static final ValidEntityKeyPredicate INSTANCE = new ValidEntityKeyPredicate();

  public static ValidEntityKeyPredicate getInstance() {
    return INSTANCE;
  }

}
