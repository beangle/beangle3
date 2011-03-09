/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.converters.Converter;
import org.beangle.security.blueprint.restrict.RestrictField;

/**
 * Store list of objects using comma.
 * <p>
 * object's property seperated by ; like this: id;name,1;group1,2;group2
 * 
 * @author chaostone
 */
public class CsvDataResolver implements DataResolver, DataProvider {

	public String marshal(RestrictField field, Collection<?> items) {
		if (null == items) { return null; }
		List<String> properties = CollectUtils.newArrayList();
		if (null != field.getKeyName()) {
			properties.add(field.getKeyName());
		}
		if (null != field.getPropertyNames()) {
			String[] names = StringUtils.split(field.getPropertyNames(), ",");
			properties.addAll(Arrays.asList(names));
		}
		StringBuilder sb = new StringBuilder();
		if (properties.isEmpty()) {
			for (Object obj : items) {
				if (null != obj) {
					sb.append(String.valueOf(obj)).append(',');
				}
			}
		} else {
			for (String property : properties) {
				sb.append(property).append(';');
			}
			sb.deleteCharAt(sb.length() - 1).append(',');
			for (Object obj : items) {
				for (String property : properties) {
					Object value = null;
					try {
						value = PropertyUtils.getProperty(obj, property);
					} catch (Exception e) {
						e.printStackTrace();
					}
					sb.append(String.valueOf(value)).append(';');
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(',');
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> unmarshal(RestrictField field, String source) {
		if (StringUtils.isEmpty(source)) { return Collections.emptyList(); }
		List<String> properties = CollectUtils.newArrayList();
		if (null != field.getKeyName()) {
			properties.add(field.getKeyName());
		}
		if (null != field.getPropertyNames()) {
			String[] names = StringUtils.split(field.getPropertyNames(), ",");
			properties.addAll(Arrays.asList(names));
		}
		String[] datas = StringUtils.split(source, ",");
		try {
			Class<?> type = null;
			type = Class.forName(field.getType());
			List<T> rs = CollectUtils.newArrayList();
			if (properties.isEmpty()) {
				ConvertUtilsBean converter = Converter.getDefault();
				for (String data : datas) {
					rs.add((T) converter.convert(data, type));
				}
				return rs;
			} else {
				properties.clear();
				int startIndex=0;
				String[] names = new String[]{field.getKeyName()};
				if(-1!=datas[0].indexOf(';')){
					names= StringUtils.split(datas[0], ";");
					startIndex=1;
				}
				properties.addAll(Arrays.asList(names));
				for (int i = startIndex; i < datas.length; i++) {
					Object obj = type.newInstance();
					String[] dataItems = StringUtils.split(datas[i], ";");
					for (int j = 0; j < properties.size(); j++) {
						BeanUtils.setProperty(obj, properties.get(j), dataItems[j]);
					}
					rs.add((T) obj);
				}
			}
			return rs;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> getData(RestrictField field, String source) {
		return unmarshal(field, source);
	}

	public String getName() {
		return "csv";
	}

}
