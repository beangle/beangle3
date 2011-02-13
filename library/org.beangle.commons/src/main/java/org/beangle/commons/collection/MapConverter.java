/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.StringUtils;

public class MapConverter {

	private ConvertUtilsBean convertUtils;

	public MapConverter() {
		this(new ConvertUtilsBean());
	}

	public MapConverter(ConvertUtilsBean convertUtils) {
		super();
		this.convertUtils = convertUtils;
	}

	public Object[] getAll(Map<String, Object> data, String attr) {
		return (Object[]) data.get(attr);
	}

	public <T> T[] getAll(Map<String, Object> data, String attr, Class<T> clazz) {
		return convert((Object[]) data.get(attr), clazz);
	}

	/**
	 * get parameter named attr
	 * 
	 * @param attr
	 * @return single value or multivalue joined with comma
	 */
	public String getString(Map<String, Object> data, String attr) {
		Object value = data.get(attr);
		if (null == value) { return null; }
		if (!value.getClass().isArray()) { return value.toString(); }
		String[] values = (String[]) value;
		if (values.length == 1) {
			return values[0];
		} else {
			return StringUtils.join(values, ',');
		}
	}

	/**
	 * get parameter named attr
	 * 
	 * @param attr
	 */
	public Object get(Map<String, Object> data, String name) {
		Object value = data.get(name);
		if (null == value) return null;
		if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			if (values.length == 1) { return values[0]; }
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(Object value, Class<T> clazz) {
		if (null == value) return null;
		if (value instanceof String && StringUtils.isEmpty((String) value)) { return null; }
		if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			if (values.length >= 1) {
				value = values[0];
			}
		}
		return (T) convertUtils.convert(value, clazz);
	}

	public <T> T[] convert(Object[] datas, Class<T> clazz) {
		if (null == datas) { return null; }
		@SuppressWarnings("unchecked")
		T[] newDatas = (T[]) Array.newInstance(clazz, datas.length);
		for (int i = 0; i < datas.length; i++) {
			newDatas[i] = convert(datas[i], clazz);
		}
		return newDatas;
	}

	public <T> T get(Map<String, Object> data, String name, Class<T> clazz) {
		return convert(get(data, name), clazz);
	}

	public Boolean getBoolean(Map<String, Object> data, String name) {
		return get(data, name, Boolean.class);
	}

	public boolean getBool(Map<String, Object> data, String name) {
		Boolean value = getBoolean(data, name);
		return (null == value) ? false : value.booleanValue();
	}

	public java.sql.Date getDate(Map<String, Object> data, String name) {
		return get(data, name, java.sql.Date.class);
	}

	public Date getDateTime(Map<String, Object> data, String name) {
		return get(data, name, Date.class);
	}

	public Float getFloat(Map<String, Object> data, String name) {
		return get(data, name, Float.class);
	}

	public Integer getInteger(Map<String, Object> data, String name) {
		return get(data, name, Integer.class);
	}

	public Long getLong(Map<String, Object> data, String name) {
		return get(data, name, Long.class);
	}

	/**
	 * 返回request中以prefix.开头的参数
	 * 
	 * @param request
	 * @param prefix
	 * @param exclusiveAttrNames
	 *            要排除的属性串
	 * @return
	 */
	public Map<String, Object> sub(Map<String, Object> data, String prefix, String exclusiveAttrNames) {
		return sub(data, prefix, exclusiveAttrNames, true);
	}

	public Map<String, Object> sub(Map<String, Object> data, String prefix) {
		return sub(data, prefix, null, true);
	}

	public Map<String, Object> sub(Map<String, Object> data, String prefix, String exclusiveAttrNames,
			boolean stripPrefix) {
		Set<String> excludes = CollectUtils.newHashSet();
		if (StringUtils.isNotEmpty(exclusiveAttrNames)) {
			String[] exclusiveAttrs = StringUtils.split(exclusiveAttrNames, ",");
			for (int i = 0; i < exclusiveAttrs.length; i++) {
				excludes.add(exclusiveAttrs[i]);
			}
		}
		Map<String, Object> newParams = CollectUtils.newHashMap();
		for (final Map.Entry<String, Object> entry : data.entrySet()) {
			final String attr = entry.getKey();
			if ((attr.indexOf(prefix + ".") == 0) && (!excludes.contains(attr))) {
				newParams.put((stripPrefix ? attr.substring(prefix.length() + 1) : attr), get(data, attr));
			}
		}
		return newParams;
	}

	public ConvertUtilsBean getConvertUtils() {
		return convertUtils;
	}

	public void setConvertUtils(ConvertUtilsBean convertUtils) {
		this.convertUtils = convertUtils;
	}

}
