/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import org.beangle.commons.text.TextResource;

/**
 * 对象属性输出接口
 * 
 * @author chaostone
 */
public interface PropertyExtractor {

	public Object getPropertyValue(Object target, String property) throws Exception;

	public TextResource getTextResource();

	public void setTextResource(TextResource textResource);

}
