/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.security.restrict.Restriction;

/**
 * 资源访问限制
 * 
 * @author chaostone
 */
@MappedSuperclass
public abstract class RestrictionBean extends LongIdObject implements Restriction {
	private static final long serialVersionUID = -1157873272781525823L;

	@NotNull
	protected RestrictPattern pattern;

	@NotNull
	protected boolean enabled = true;

	@ElementCollection
	@MapKeyColumn(name = "field_id")
	@Column(name = "content", length = 2000)
	@CollectionTable(joinColumns = @JoinColumn(name = "restriction_id"))
	private Map<Long, String> items = CollectUtils.newHashMap();

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
