/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.xwork.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Select extends UIBean {

	protected String name;

	private Object items;
	private String empty;
	private Object value;

	private String keyName;
	private String valueName;

	private String label;

	public Select(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == keyName) {
			if (items instanceof Map<?, ?>) {
				keyName = "key";
				valueName = "value";
				items = ((Map<?, ?>) items).entrySet();
			} else {
				keyName = "id";
				valueName = "name";
			}
		}
		if (null == this.id) id = name;
		if (null != label) label = getText(label, label);
	}

	public boolean isSelected(Object obj) {
		if (null == value) return false;
		else try {
			return value.equals(obj) || value.equals(PropertyUtils.getSimpleProperty(obj, keyName));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getValueName() {
		return valueName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOption(String option) {
		if (null != option) {
			if (StringUtils.contains(option, ",")) {
				keyName = StringUtils.substringBefore(option, ",");
				valueName = StringUtils.substringAfter(option, ",");
			}
		}
	}
}
