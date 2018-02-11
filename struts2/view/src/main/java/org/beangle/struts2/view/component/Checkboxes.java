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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Checkboxes extends UIBean {

  private String name;

  private String label;

  private Object items;

  private Checkbox[] checkboxes;

  private Object value;

  private String comment;

  private String required;

  private Object min;

  private Object max;

  private String valueName = "name";

  public Checkboxes(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    if (null != label) label = getText(label);

    List<?> keys = convertItems();
    List<?> values = convertValue();
    checkboxes = new Checkbox[keys.size()];
    int i = 0;
    Form myform = findAncestor(Form.class);
    Integer minValue = getValidateNum(min);
    Integer maxValue = getValidateNum(max);
    if (null != myform) {
      if ("true".equals(required)) {
        myform.addCheck(id, "assert($(\"input[name='" + name + "']:checked\").length != 0,'必须勾选一项')");
      }
    }
    if (minValue != null && maxValue != null) {
      if (minValue > maxValue) {
        Integer temp = maxValue;
        maxValue = minValue;
        minValue = temp;
      }
    }
    if (minValue != null && minValue > 0 && minValue <= checkboxes.length) {
      myform.addCheck(id, "assert($(\"input[name='" + name + "']:checked\").length >= " + minValue + ",'至少勾选"
          + minValue + "项')");
    }
    if (maxValue != null && maxValue < checkboxes.length && maxValue > 0) {
      myform.addCheck(id, "assert($(\"input[name='" + name + "']:checked\").length <= " + maxValue + ",'最多勾选"
          + maxValue + "项')");
    }
    for (Object key : keys) {
      checkboxes[i] = new Checkbox(stack);
      checkboxes[i].setTitle(String.valueOf(((Map<?, ?>) items).get(key)));
      checkboxes[i].setValue(key);
      checkboxes[i].setId(Strings.concat(this.id + "_" + String.valueOf(i)));
      if (CollectUtils.isNotEmpty(values) && values.contains(key)) {
        checkboxes[i].setChecked(true);
      }
      checkboxes[i].evaluateParams();
      i++;
    }
  }

  @SuppressWarnings("unchecked")
  private List<?> convertItems() {
    if (items instanceof Map) return CollectUtils.newArrayList(((Map<Object, Object>) items).keySet());
    Map<Object, Object> itemMap = new TreeMap<Object, Object>();
    List<Object> keys = CollectUtils.newArrayList();
    if (items instanceof String) {
      if (Strings.isBlank((String) items)) {
        return CollectUtils.newArrayList();
      } else {
        String[] titleArray = Strings.split(items.toString(), ',');
        for (int i = 0; i < titleArray.length; i++) {
          String titleValue = titleArray[i];
          int semiconIndex = titleValue.indexOf(':');
          if (-1 != semiconIndex) {
            keys.add(titleValue.substring(0, semiconIndex));
            itemMap.put(titleValue.substring(0, semiconIndex), titleValue.substring(semiconIndex + 1));
          }
        }
      }
    } else if (items instanceof List) {
      for (Object object : (List<?>) items) {
        try {
          Object value = PropertyUtils.getProperty(object, "id");
          Object title = PropertyUtils.getProperty(object, valueName);
          keys.add(value);
          itemMap.put(value, title);
        } catch (Exception e) {
          return CollectUtils.newArrayList();
        }
      }
    }
    items = itemMap;
    return keys;
  }

  private Integer getValidateNum(Object number) {
    try {
      return Integer.parseInt(String.valueOf(number));
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private List<?> convertValue() {
    List<Object> values = CollectUtils.newArrayList();
    if (value instanceof List) {
      try {
        for (Object object : (List<Object>) value) {
          values.add(PropertyUtils.getProperty(object, "id"));
        }
        return values;
      } catch (Exception e) {
        return CollectUtils.newArrayList();
      }
    } else if (value instanceof Object[]) {
      if (value != null && ((Object[]) value).length > 0) {
        return Arrays.asList((Object[]) value);
      } else {
        return CollectUtils.newArrayList();
      }
    } else {
      String valueStr = String.valueOf(value);
      if (Strings.isNotBlank(valueStr)) {
        return Arrays.asList(valueStr.split(","));
      } else {
        return CollectUtils.newArrayList();
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Object getItems() {
    return items;
  }

  public void setItems(Object items) {
    this.items = items;
  }

  public Checkbox[] getCheckboxes() {
    return checkboxes;
  }

  public void setCheckboxes(Checkbox[] checkboxes) {
    this.checkboxes = checkboxes;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
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

  public Object getMin() {
    return min;
  }

  public void setMin(Object min) {
    this.min = min;
  }

  public Object getMax() {
    return max;
  }

  public void setMax(Object max) {
    this.max = max;
  }

  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }

}
