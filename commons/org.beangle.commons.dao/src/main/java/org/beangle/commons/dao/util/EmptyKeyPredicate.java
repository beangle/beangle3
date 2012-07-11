/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.util;

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
