/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Select extends ClosingUIBean {

  protected String name;

  private Object items = Collections.emptyList();
  private String empty;
  private Object value;

  private String keyName;
  private String valueName;

  private String label;
  protected String title;

  protected String comment;
  protected String check;
  protected String required;

  /** option text template */
  protected String option;

  public Select(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == keyName) {
      if (items instanceof Map<?, ?>) {
        keyName = "key";
        valueName = "value";
        items = ((Map<?, ?>) items).entrySet();
      } else {
        keyName = "id";
        valueName = "name";
      }
    }
    if (null == this.id) generateIdIfEmpty();
    if (null != label) label = getText(label);
    if (null != title) {
      title = getText(title);
    } else {
      title = label;
    }
    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addCheck(id, "require()");
      if (null != check) myform.addCheck(id, check);
    }
    if (null == value) {
      value = getRequestParameter(name);
    }
  }

  public boolean isSelected(Object obj) {
    if (null == value) return false;
    else try {
      if (value instanceof String) {
        return value.equals(obj)
            || value.equals(String.valueOf(PropertyUtils.getSimpleProperty(obj, keyName)));
      } else {
        return value.equals(obj) || value.equals(PropertyUtils.getSimpleProperty(obj, keyName));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getItems() {
    return items;
  }

  public void setItems(Object items) {
    this.items = items;
  }

  public String getEmpty() {
    return empty;
  }

  public void setEmpty(String empty) {
    this.empty = empty;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String getKeyName() {
    return keyName;
  }

  public String getValueName() {
    return valueName;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setOption(String option) {
    if (null != option) {
      if (Strings.contains(option, "$")) {
        this.option = option;
      } else if (Strings.contains(option, ",")) {
        keyName = Strings.substringBefore(option, ",");
        valueName = Strings.substringAfter(option, ",");
      }
    }
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCheck() {
    return check;
  }

  public void setCheck(String check) {
    this.check = check;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public String getOption() {
    return option;
  }

}
