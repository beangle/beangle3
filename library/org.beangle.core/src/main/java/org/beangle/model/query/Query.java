/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query;

import java.util.Map;

/**
 * 数据查询接口
 * 
 * @author chaostone
 */
public interface Query<T> {

	public String getStatement();

	public Map<String, Object> getParams();

	public boolean isCacheable();

	public Lang getLang();
}
