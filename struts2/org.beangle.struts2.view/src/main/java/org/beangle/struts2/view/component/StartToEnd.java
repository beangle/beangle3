package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class StartToEnd extends UIBean {

	private String names;

	private String values;

	private String comments;

	private String requireds;

	private String format = "yyyy-MM-dd";

	private Date[] dates;

	public StartToEnd(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		String[] nameArray = StringUtils.split(names, ',');
		dates = new Date[nameArray.length];
		String[] requiredArray = StringUtils.split(requireds, ',');
		String[] commentArray = StringUtils.split(comments, ',');
		String[] valueArray = StringUtils.split(values, ',');
		for (int i = 0; i < nameArray.length; i++) {
			dates[i] = new Date(stack);
			String name = nameArray[i];
			String title = name;
			int semiconIndex = name.indexOf(';');
			if (-1 != semiconIndex) {
				title = name.substring(semiconIndex + 1);
				name = name.substring(0, semiconIndex);
			}
			if (requiredArray != null) {
				dates[i].setRequired(requiredArray.length == 1 ? requireds : requiredArray[i]);
			}
			if (commentArray != null) {
				dates[i].setComment(commentArray.length == 1 ? comments : commentArray[i]);
			}
			if (valueArray != null) {
				dates[i].setValue(valueArray.length == 1 ? values : valueArray[i]);
			}
			dates[i].setName(name);
			dates[i].setLabel(title);
			dates[i].setFormat(format);
			dates[i].evaluateParams();
		}
		if (dates.length == 2) {
			dates[0].setMaxDate("#F{$dp.$D(\\'" + dates[1].id + "\\')}");
			dates[1].setMinDate("#F{$dp.$D(\\'" + dates[0].id + "\\')}");
		}

	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
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

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRequireds() {
		return requireds;
	}

	public void setRequireds(String requireds) {
		this.requireds = requireds;
	}

}
