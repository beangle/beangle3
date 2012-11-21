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

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;

/**
 * 有效实体判断谓词
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ValidEntityPredicate implements Predicate {

  /** {@inheritDoc} */
  public boolean evaluate(final Object value) {
    if (null == value) { return false; }
    try {
      Serializable key = (Serializable) PropertyUtils.getProperty(value, "id");
      return ValidEntityKeyPredicate.getInstance().evaluate(key);
    } catch (Exception e) {
      return false;
    }
  }

  /** Constant <code>INSTANCE</code> */
  public static final ValidEntityPredicate INSTANCE = new ValidEntityPredicate();

  /**
   * <p>
   * getInstance.
   * </p>
   * 
   * @return a {@link org.beangle.commons.entity.util.ValidEntityPredicate} object.
   */
  public static ValidEntityPredicate getInstance() {
    return INSTANCE;
  }
}
