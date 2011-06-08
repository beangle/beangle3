/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Textarea.java May 3, 2011 12:40:21 PM chaostone $
 */
public class Textarea extends AbstractTextBean {

	protected String cols;
	protected String readonly;
	protected String rows;
	protected String wrap;

	public Textarea(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		super.evaluateParams();
		Form myform = findAncestor(Form.class);
		if (null != maxlength) myform.addCheck(id, "maxLength(" + maxlength + ")");
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getWrap() {
		return wrap;
	}

	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

}
