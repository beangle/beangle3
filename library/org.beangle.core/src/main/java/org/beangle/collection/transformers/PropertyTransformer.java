/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.collection.transformers;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;

/**
 * bean属性提取器<br>
 * CollectionUtls.transform(collections,new PropertyTransformer('myAttr'))
 * 
 * @author chaostone
 */
public class PropertyTransformer implements Transformer {

	private String property;

	public PropertyTransformer(final String property) {
		super();
		this.property = property;
	}

	public PropertyTransformer() {
		super();
	}

	public Object transform(final Object arg0) {
		try {
			return PropertyUtils.getProperty(arg0, property);
		} catch (Exception e) {
			return null;
		}
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

}
