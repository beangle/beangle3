/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.converters.Converter;
import org.beangle.commons.lang.StrUtils;

/**
 * @author chaostone
 * @version $Id: ConstDataProvider.java Nov 9, 2010 7:59:14 PM chaostone $
 */
public class ConstDataProvider implements DataProvider {

	@SuppressWarnings("unchecked")
	public <T> List<T> getData(Class<T> type, String source) {
		if (StringUtils.isEmpty(source)) { return Collections.emptyList(); }
		String[] datas = StrUtils.split(source);
		List<T> rs = CollectUtils.newArrayList();
		ConvertUtilsBean converter = Converter.getDefault();
		for (String data : datas) {
			rs.add((T) converter.convert(data, type));
		}
		return rs;
	}

	public String asString(List<?> objects) {
		if (null == objects) { return null; }
		StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			if (null != obj) {
				sb.append(String.valueOf(obj)).append(',');
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getName() {
		return "const";
	}

}
