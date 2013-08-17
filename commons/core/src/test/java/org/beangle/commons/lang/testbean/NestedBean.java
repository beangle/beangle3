package org.beangle.commons.lang.testbean;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public class NestedBean {

  Long id;
  
  Map<Object,Object> datas=CollectUtils.newHashMap();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Map<Object, Object> getDatas() {
    return datas;
  }

  public void setDatas(Map<Object, Object> datas) {
    this.datas = datas;
  }
  
  
}
