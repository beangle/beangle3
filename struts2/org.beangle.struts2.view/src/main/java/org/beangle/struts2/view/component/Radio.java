package org.beangle.struts2.view.component;

import org.beangle.struts2.view.component.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class Radio extends UIBean {

	public Radio(ValueStack stack) {
		super(stack);
	}

	protected String name;
	protected String label;
	protected String title;
	protected String value = "";

	@Override
	protected void evaluateParams() {
		if (null == this.id) generateIdIfEmpty();
		if (null != label) label = getText(label);
		if (null != title) {
			title = getText(title);
		} else {
			title = label;
		}
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
