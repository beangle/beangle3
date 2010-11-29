/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ognl.Ognl;
import ognl.OgnlException;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.TextResource;

/**
 * 缺省和简单的属性提取类
 * 
 * @author chaostone
 */
public class DefaultPropertyExtractor implements PropertyExtractor {

	protected TextResource textResource = null;

	protected Map<String, Object> expressions = CollectUtils.newHashMap();

	protected Set<String> errorProperties = CollectUtils.newHashSet();

	public DefaultPropertyExtractor() {
		super();
	}

	public DefaultPropertyExtractor(TextResource textResource) {
		setTextResource(textResource);
	}

	protected Object extract(Object target, String property) throws Exception {
		if (errorProperties.contains(property)) return null;
		Object exp = expressions.get(property);
		if (null == exp) {
			try {
				exp = Ognl.parseExpression(property);
				expressions.put(property, exp);
			} catch (OgnlException e) {
				errorProperties.add(property);
				return null;
			}
		}
		Object value = null;
		try {
			value = Ognl.getValue(exp, target);
		} catch (OgnlException e) {
			return null;
		}
		if (value instanceof Boolean) {
			if (null == textResource) {
				return value;
			} else {
				if (Boolean.TRUE.equals(value)) return getText("yes");
				else return getText("no");
			}
		} else {
			return value;
		}
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		return extract(target, property);
	}

	public String getPropertyIn(Collection<?> collection, String property) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (null != collection) {
			for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
				Object one = iter.next();
				sb.append(extract(one, property)).append(",");
			}
		}
		// 删除逗号
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	protected String getText(String key) {
		return textResource.getText(key, key);
	}

	public TextResource getTextResource() {
		return textResource;
	}

	public void setTextResource(TextResource textResource) {
		this.textResource = textResource;
	}
}
