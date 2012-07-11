package org.beangle.struts2.view.component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Radios extends UIBean {

  private String name;

  private String label;

  private Object items;

  private Radio[] radios;

  private Object value;

  private String comment;

  public Radios(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null == this.id) generateIdIfEmpty();
    if (null != label) label = getText(label);
    else label = getText(name);

    List<?> keys = convertItems();
    radios = new Radio[keys.size()];
    int i = 0;
    for (Object key : keys) {
      radios[i] = new Radio(stack);
      radios[i].setTitle(String.valueOf(((Map<?, ?>) items).get(key)));
      radios[i].setValue(key);
      radios[i].setId(Strings.concat(this.id + "_" + String.valueOf(i)));
      radios[i].evaluateParams();
      i++;
    }
    if (null == this.value && radios.length > 0) this.value = radios[0].getValue();
    this.value = Radio.booleanize(this.value);

  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private List<?> convertItems() {
    if (items instanceof Map) return CollectUtils.newArrayList(((Map<Object, Object>) items).keySet());
    Map<Object, Object> defaultItemMap = new TreeMap<Object, Object>();
    defaultItemMap.put("1", "是");
    defaultItemMap.put("0", "否");
    List<?> defaultKeys = CollectUtils.newArrayList("1", "0");
    Map<Object, Object> itemMap = new TreeMap<Object, Object>();
    List keys = CollectUtils.newArrayList();
    if (null == items) {
      keys = defaultKeys;
      items = defaultItemMap;
    } else if (items instanceof String) {
      if (Strings.isBlank((String) items)) {
        keys = defaultKeys;
        items = defaultItemMap;
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
        items = itemMap;
      }
    }
    return keys;
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

  public Radio[] getRadios() {
    return radios;
  }

  public void setRadios(Radio[] radios) {
    this.radios = radios;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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

}
