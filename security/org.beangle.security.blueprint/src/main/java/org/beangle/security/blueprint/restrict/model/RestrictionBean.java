/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictPattern;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

/**
 * 资源访问限制
 * 
 * @author chaostone
 */
public class RestrictionBean extends LongIdObject implements Restriction {
	private static final long serialVersionUID = -1157873272781525823L;

	private RestrictionHolder holder;

	private RestrictPattern pattern;

	private boolean enabled = true;

	private Map<Long, String> items = CollectUtils.newHashMap();

	public RestrictionBean() {
		super();
	}

	public RestrictionBean(RestrictionHolder holder, RestrictPattern pattern) {
		super();
		this.holder = holder;
		this.pattern = pattern;
	}

	public RestrictionHolder getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder holder) {
		this.holder = holder;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public RestrictPattern getPattern() {
		return pattern;
	}

	public Map<Long, String> getItems() {
		return items;
	}

	public void setItems(Map<Long, String> items) {
		this.items = items;
	}

	public void setPattern(RestrictPattern pattern) {
		this.pattern = pattern;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getItem(String paramName) {
		RestrictField param = getPattern().getEntity().getField(paramName);
		if (null == param) {
			return null;
		} else {
			return getItem(param);
		}
	}

	public String getItem(RestrictField param) {
		if (null == items || items.isEmpty()) {
			return null;
		} else {
			return (String) items.get(param.getId());
		}
	}

	public void setItem(RestrictField param, String text) {
		items.put(param.getId(), text);
	}

	public void merge(RestrictField param, String value) {
		setItem(param, evictComma(StrUtils.mergeSeq(getItem(param), value)));
	}

	public void shrink(RestrictField param, String value) {
		setItem(param, evictComma(StrUtils.subtractSeq(getItem(param), value)));
	}

	private static String evictComma(String str) {
		if (StringUtils.isEmpty(str)) return str;
		else {
			if (str.startsWith(",") && str.endsWith(",")) return str.substring(1, str.length() - 1);
			else if (str.startsWith(",")) {
				return str.substring(1);
			} else if (str.endsWith(",")) {
				return str.substring(0, str.length() - 1);
			} else {
				return str;
			}
		}
	}

}
