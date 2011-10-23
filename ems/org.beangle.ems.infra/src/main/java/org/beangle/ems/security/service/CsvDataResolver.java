/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

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
import org.beangle.ems.security.profile.UserPropertyMeta;

/**
 * Store list of objects using comma.
 * <p>
 * object's property seperated by ; like this: id;name,1;group1,2;group2
 * 
 * @author chaostone
 */
public class CsvDataResolver implements UserDataResolver, UserDataProvider {

	public String marshal(UserPropertyMeta property, Collection<?> items) {
		if (null == items) { return null; }
		List<String> properties = CollectUtils.newArrayList();
		if (null != property.getKeyName()) {
			properties.add(property.getKeyName());
		}
		if (null != property.getPropertyNames()) {
			String[] names = StringUtils.split(property.getPropertyNames(), ",");
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
			for (String prop : properties) {
				sb.append(prop).append(';');
			}
			sb.deleteCharAt(sb.length() - 1).append(',');
			for (Object obj : items) {
				for (String prop : properties) {
					Object value = null;
					try {
						value = PropertyUtils.getProperty(obj, prop);
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
	public <T> List<T> unmarshal(UserPropertyMeta property, String source) {
		if (StringUtils.isEmpty(source)) { return Collections.emptyList(); }
		List<String> properties = CollectUtils.newArrayList();
		if (null != property.getKeyName()) {
			properties.add(property.getKeyName());
		}
		if (null != property.getPropertyNames()) {
			String[] names = StringUtils.split(property.getPropertyNames(), ",");
			properties.addAll(Arrays.asList(names));
		}
		String[] datas = StringUtils.split(source, ",");
		try {
			Class<?> type = null;
			type = Class.forName(property.getValueType());
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
				String[] names = new String[]{property.getKeyName()};
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

	public <T> List<T> getData(UserPropertyMeta property, String source) {
		return unmarshal(property, source);
	}

	public String getName() {
		return "csv";
	}

}
