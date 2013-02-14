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
package org.beangle.commons.entity.util;

import org.beangle.commons.lang.functor.NotEmptyStringPredicate;
import org.beangle.commons.lang.functor.NotZeroNumberPredicate;
import org.beangle.commons.lang.functor.Predicate;

/**
 * 判断实体类中的主键是否是有效主键
 * 
 * @author chaostone
 */
public class ValidEntityKeyPredicate implements Predicate<Object> {

  public Boolean apply(Object value) {
    if (null == value) return Boolean.FALSE;
    if (value instanceof Number) return NotZeroNumberPredicate.Instance.apply((Number) value);
    return NotEmptyStringPredicate.Instance.apply(value.toString());
  }

  public static final ValidEntityKeyPredicate Instance = new ValidEntityKeyPredicate();

}
