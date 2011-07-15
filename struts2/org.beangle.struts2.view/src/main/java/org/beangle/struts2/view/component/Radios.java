package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;
import org.beangle.struts2.view.component.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class Radios extends UIBean {

	private String name;

	private String label;

	private Object titles;

	private Radio[] radios;

	private Object checked;

	private String comment;

	public Radios(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void evaluateParams() {
		if (null == this.id) generateIdIfEmpty();
		String[] titleArray;
		if (titles == null) {
			titleArray = new String[] { "1:是", "0:否" };
		} else {
			titleArray = StringUtils.split(titles.toString(), ',');
		}
		radios = new Radio[titleArray.length];
		if (null != label) label = getText(label);
		for (int i = 0; i < titleArray.length; i++) {
			radios[i] = new Radio(stack);
			String titleValue = titleArray[i];
			String value = "";
			String title = "";
			int semiconIndex = titleValue.indexOf(':');
			if (-1 != semiconIndex) {
				title = titleValue.substring(semiconIndex + 1);
				value = titleValue.substring(0, semiconIndex);
			}
			radios[i].setTitle(title);
			radios[i].setValue(value);
			radios[i].evaluateParams();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getTitles() {
		return titles;
	}

	public void setTitles(Object titles) {
		this.titles = titles;
	}

	public Radio[] getRadios() {
		return radios;
	}

	public void setRadios(Radio[] radios) {
		this.radios = radios;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getChecked() {
		return checked;
	}

	public void setChecked(Object checked) {
		this.checked = checked;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
