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

import java.util.Collections;
import java.util.Map;

import org.beangle.commons.bean.PropertyUtils;
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
    label = processLabel(label, name);
    if (null != title) title = getText(title);
    else title = label;

    Form myform = findAncestor(Form.class);
    if (null != myform) {
      if ("true".equals(required)) myform.addRequire(id);
      if (null != check) myform.addCheck(id, check);
    }
    if (null == value) value = getRequestParameter(name);
    // trim empty string to null for speed up isSelected
    if ((value instanceof String) && Strings.isEmpty((String) value)) value = null;
  }

  public boolean isSelected(Object obj) {
    if (null == value) return false;
    else try {
      Object nobj = obj;
      if (obj instanceof Map.Entry<?, ?>) {
        nobj = ((Map.Entry<?, ?>) obj).getKey();
        if (keyName.equals("key")) {
          return value.equals(nobj)||value.toString().equals(nobj.toString());
        } else {
          boolean rs = value.equals(nobj) || value.equals(PropertyUtils.getProperty(nobj, keyName));
          return rs || value.toString().equals(nobj.toString())
              || value.toString().equals(String.valueOf(PropertyUtils.getProperty(nobj, keyName)));
        }
      } else {
        boolean rs = value.equals(nobj) || value.equals(PropertyUtils.getProperty(nobj, keyName));
        return rs || value.toString().equals(nobj.toString())
            || value.toString().equals(String.valueOf(PropertyUtils.getProperty(nobj, keyName)));
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
