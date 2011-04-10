package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Date extends UIBean {

	private String name;

	private String label;

	public Date(ValueStack stack) {
		super(stack);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
