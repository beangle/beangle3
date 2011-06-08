/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.xwork.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Select2.java May 2, 2011 10:09:28 AM chaostone $
 */
public class Select2 extends UIBean {

	private String label;
	private String required;
	private String keyName = "id", valueName = "name";
	private String name1st, name2nd;
	private Object items1st, items2nd;
	private String size = "10";

	public Select2(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null != label) label = getText(label);
	}

	public String getName1st() {
		return name1st;
	}

	public void setName1st(String name1st) {
		this.name1st = name1st;
	}

	public String getName2nd() {
		return name2nd;
	}

	public void setName2nd(String name2nd) {
		this.name2nd = name2nd;
	}

	public Object getItems1st() {
		return items1st;
	}

	public void setItems1st(Object items1st) {
		this.items1st = items1st;
	}

	public Object getItems2nd() {
		return items2nd;
	}

	public void setItems2nd(Object items2nd) {
		this.items2nd = items2nd;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setOption(String option) {
		if (null != option) {
			if (StringUtils.contains(option, ",")) {
				keyName = StringUtils.substringBefore(option, ",");
				valueName = StringUtils.substringAfter(option, ",");
			}
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

}
