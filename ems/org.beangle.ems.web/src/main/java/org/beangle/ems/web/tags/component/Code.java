package org.beangle.ems.web.tags.component;

import org.beangle.struts2.view.component.Select;

import com.opensymphony.xwork2.util.ValueStack;

public class Code extends Select {

	private String className;

	private String format;

	public Code(ValueStack stack) {
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
