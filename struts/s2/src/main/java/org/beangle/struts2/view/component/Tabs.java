/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.view.component;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Tabs.java Jul 16, 2011 3:04:17 PM chaostone $
 */
public class Tabs extends ClosingUIBean {

  private String selectedTab;

  private String style="font-size:1em;font-family: inherit;";

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

  public String getStyle(){
	  return style;
  }
  public void setStyle(String s){
	  this.style=s;
  }
  public List<Tab> getTabs() {
    return tabs;
  }

}
