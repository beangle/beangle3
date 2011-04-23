/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Navmenu.java Apr 20, 2011 8:47:00 AM chaostone $
 */
public class Navmenu extends ClosingUIBean {

	private String title;

	private String uri;

	/** 是有已经有标签卡被选中了 */
	private boolean selected;

	public Navmenu(ValueStack stack) {
		super(stack);
		this.uri = getRequestURI();
	}

	boolean isSelected(String givenUri) {
		if (selected) return false;
		else {
			selected = sameAction(givenUri, uri);
			return selected;
		}
	}

	/**
	 * 去除后缀比较是否是同一个action
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private boolean sameAction(String first, String second) {
		StringBuilder firstSb = new StringBuilder(StringUtils.substringBeforeLast(first, "."));
		if (-1 == firstSb.lastIndexOf("!")) {
			firstSb.append("!index");
		}
		StringBuilder secondSb = new StringBuilder(StringUtils.substringBeforeLast(second, "."));
		if (-1 == secondSb.lastIndexOf("!")) {
			secondSb.append("!index");
		}
		return firstSb.toString().equals(secondSb.toString());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
