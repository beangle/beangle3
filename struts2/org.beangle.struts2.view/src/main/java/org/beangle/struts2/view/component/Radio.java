package org.beangle.struts2.view.component;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.util.ValueStack;

public class Radio extends UIBean {
	public static final Map<Object, String> Booleans = new HashMap<Object, String>();
	static {
		Booleans.put(Boolean.TRUE, "1");
		Booleans.put(Boolean.FALSE, "0");
		Booleans.put("y", "1");
		Booleans.put("Y", "1");
		Booleans.put("true", "1");
		Booleans.put("N", "0");
		Booleans.put("n", "0");
		Booleans.put("false", "0");
	}

	public Radio(ValueStack stack) {
		super(stack);
	}

	protected String name;
	protected String label;
	protected String title;
	protected Object value = "";

	@Override
	protected void evaluateParams() {
		if (null == this.id) generateIdIfEmpty();
		if (null != label) label = getText(label);
		if (null != title) {
			title = getText(title);
		} else {
			title = label;
		}
		this.value = booleanize(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static Object booleanize(Object obj) {
		Object booleanValue = Booleans.get(obj);
		return null == booleanValue ? obj : booleanValue;
	}
}
