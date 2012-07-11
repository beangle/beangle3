/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.transformers;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * Transform map to CaseInsensitive One.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CaseInsensitiveMapTransformer {

  /**
   * <p>
   * transform.
   * </p>
   * 
   * @param object a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object transform(Object object) {
    @SuppressWarnings("unchecked")
    Map<String, ?> m = (Map<String, ?>) object;
    return new CaseInsensitiveMap(m);
  }
}
