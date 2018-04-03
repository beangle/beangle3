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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.apache.commons.collections.MapUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Form extends ClosingUIBean {

  protected String name;
  protected String action;
  protected String target;

  protected String onsubmit;
  /** Boolean */
  protected String validate;
  private String title;

  private Map<String, StringBuilder> elementChecks = CollectUtils.newHashMap();

  private StringBuilder extraChecks;

  public Form(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == name && null == id) {
      generateIdIfEmpty();
      name = id;
    } else if (null == id) {
      id = name;
    }
    action = render(action);
    if (null != title) title = getText(title);
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOnsubmit() {
    return onsubmit;
  }

  public void setOnsubmit(String onsubmit) {
    this.onsubmit = onsubmit;
  }

  public String getValidate() {
    if (null == validate) {
      validate = String.valueOf(MapUtils.isNotEmpty(elementChecks) || StringUtils.isNotBlank(extraChecks));
    }
    return validate;
  }

  public void setValidate(String validate) {
    this.validate = validate;
  }

  /**
   * Required element by id;
   *
   * @param id
   */
  public void addRequire(String id) {
    this.addCheck(id, "require().match('notBlank')");
  }

  public void addCheck(String id, String check) {
    StringBuilder sb = elementChecks.get(id);
    if (null == sb) {
      sb = new StringBuilder(100);
      elementChecks.put(id, sb);
    }
    sb.append('.').append(check);
  }

  public void addCheck(String check) {
    if (null == extraChecks) extraChecks = new StringBuilder();
    extraChecks.append(check);
  }

  public String getValidity() {
    // every element initial validity buffer is 80 chars.
    StringBuilder sb = new StringBuilder((elementChecks.size() * 80)
        + ((null == extraChecks) ? 0 : extraChecks.length()));
    for (Map.Entry<String, StringBuilder> check : elementChecks.entrySet()) {
      sb.append("jQuery('#").append(Strings.replace(check.getKey(), ".", "\\\\.")).append("')")
          .append(check.getValue()).append(";\n");
    }
    if (null != extraChecks) sb.append(extraChecks);
    return sb.toString();
  }

}
