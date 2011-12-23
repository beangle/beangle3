package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Startend extends UIBean {

	private String label;

	private String name;

	private Object start;

	private Object end;

	private String comment;

	private String required;

	private String format = "yyyy-MM-dd";

	private Date[] dates;

	public Startend(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		String[] nameArray = StringUtils.split(name, ',');
		dates = new Date[nameArray.length];
		String format2 = Date.ResvervedFormats.get(format);
		if (null != format2) format = format2;
		String[] requiredArray = StringUtils.split(required, ',');
		String[] commentArray = StringUtils.split(comment, ',');
		String[] labelArray = StringUtils.split(label, ',');
		for (int i = 0; i < nameArray.length; i++) {
			if (i >= 2) break;
			dates[i] = new Date(stack);
			String name = nameArray[i];
			dates[i].setName(name);
			dates[i].setFormat(format);
			if (requiredArray != null) {
				dates[i].setRequired(requiredArray.length == 1 ? required : requiredArray[i]);
			}
			if (commentArray != null) {
				dates[i].setComment(commentArray.length == 1 ? comment : commentArray[i]);
			}
			if (labelArray != null) {
				dates[i].setLabel(labelArray.length == 1 ? label : labelArray[i]);
			}
			dates[i].setTitle(dates[i].getLabel());
			if (i == 0) dates[0].setValue(start);
			else dates[1].setValue(end);

			dates[i].evaluateParams();
		}
		if (dates.length == 2) {
			dates[0].setMaxDate("#F{$dp.$D(\\'" + dates[1].id + "\\')}");
			dates[1].setMinDate("#F{$dp.$D(\\'" + dates[0].id + "\\')}");

			if (labelArray.length == 1) {
				boolean containTime = format.contains("HH:mm");
				dates[0].setTitle(dates[0].getTitle()
						+ getText(containTime ? "common.beginAt" : "common.beginOn"));
				dates[1].setTitle(dates[1].getTitle()
						+ getText(containTime ? "common.endAt" : "common.endOn"));
			}
		}

	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Date[] getDates() {
		return dates;
	}

	public void setDates(Date[] dates) {
		this.dates = dates;
	}

	public Object getStart() {
		return start;
	}

	public void setStart(Object start) {
		this.start = start;
	}

	public Object getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		this.end = end;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

}
