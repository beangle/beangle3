/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.List;

import org.beangle.collection.CollectUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Tabs.java Jul 16, 2011 3:04:17 PM chaostone $
 */
public class Tabs extends ClosingUIBean {

	private String selectedTab;

	private List<Tab> tabs = CollectUtils.newArrayList();

	void addTab(Tab tab) {
		this.tabs.add(tab);
	}

	public Tabs(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		generateIdIfEmpty();
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

}
