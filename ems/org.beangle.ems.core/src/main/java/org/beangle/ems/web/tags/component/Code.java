package org.beangle.ems.web.tags.component;

import org.beangle.struts2.view.component.Select;

import com.opensymphony.xwork2.util.ValueStack;

public class Code extends Select {

	private String type;

	private String format;

	public Code(ValueStack stack) {
		super(stack);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
