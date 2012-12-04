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
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * EmptyKeyPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class EmptyKeyPredicate implements Predicate {

  /** {@inheritDoc} */
  public boolean evaluate(final Object value) {
    boolean success = false;
    if (null != value) {
      if (value instanceof String) {
        success = Strings.isEmpty((String) value);
      } else if (value instanceof Number) {
        success = (0 == ((Number) value).intValue());
      } else {
        throw new RuntimeException("unsupported key type");
      }
    }
    return success;
  }

}
