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
 */
public class CaseInsensitiveMapTransformer {

	public Object transform(Object object) {
		@SuppressWarnings("unchecked")
		Map<String, ?> m = (Map<String, ?>) object;
		return new CaseInsensitiveMap(m);
	}
}
