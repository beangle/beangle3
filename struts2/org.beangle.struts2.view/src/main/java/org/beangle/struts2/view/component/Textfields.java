/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Textfields extends UIBean {

	private String names;

	private Textfield[] fields;

	public Textfields(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		String[] nameArray = StringUtils.split(names, ',');
		fields = new Textfield[nameArray.length];
		for (int i = 0; i < nameArray.length; i++) {
			fields[i] = new Textfield(stack);
			String name = nameArray[i];
			String title = name;
			int semiconIndex = name.indexOf(';');
			if (-1 != semiconIndex) {
				title = name.substring(semiconIndex + 1);
				name = name.substring(0, semiconIndex);
			}
			fields[i].setName(name);
			fields[i].setLabel(title);
			fields[i].evaluateParams();
		}
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Textfield[] getFields() {
		return fields;
	}

}
