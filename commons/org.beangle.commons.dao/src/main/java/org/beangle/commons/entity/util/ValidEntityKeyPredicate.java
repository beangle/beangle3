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
