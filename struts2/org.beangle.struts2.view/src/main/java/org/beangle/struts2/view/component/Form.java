/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

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
      if (!elementChecks.isEmpty()) validate = "true";
      else validate = "false";
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
