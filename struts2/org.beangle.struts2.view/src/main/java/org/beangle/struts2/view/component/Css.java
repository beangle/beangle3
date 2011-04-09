package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Css extends UIBean {

	private String href;

	public Css(ValueStack stack) {
		super(stack);
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
