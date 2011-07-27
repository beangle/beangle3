package org.beangle.struts2.view.component;

import org.beangle.struts2.view.component.Select;

import com.opensymphony.xwork2.util.ValueStack;

public class BaseCode extends Select {

	private String className;

	private String format;

	public BaseCode(ValueStack stack) {
		super(stack);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
