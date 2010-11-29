/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public class Context {

	Map<String, Object> datas = CollectUtils.newHashMap();

	public static final String KEYS = "keys";
	public static final String TITLES = "titles";
	public static final String EXTRACTOR = "extractor";

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}

	public void put(String key, Object obj) {
		datas.put(key, obj);
	}

	public Object get(String key) {
		return datas.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		return (T) datas.get(key);
	}

}
