/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.bean.comparators;

import org.apache.commons.lang.StringUtils;

/**
 * 多个属性的比较
 * 
 * @author chaostone
 */
public class MultiPropertyComparator<T> extends ChainComparator<T> {

	public MultiPropertyComparator(final String propertyStr) {
		super();
		final String[] properties = StringUtils.split(propertyStr, ",");
		for (int i = 0; i < properties.length; i++) {
			addComparator(new PropertyComparator<T>(properties[i].trim()));
		}
	}

}
