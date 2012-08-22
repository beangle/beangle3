package org.beangle.commons.collection.predicates;

import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.CollectUtils;

public class PropertyPredicate implements Predicate {

  String script;
  Map<String, Object> params = CollectUtils.newHashMap();

  private PropertyPredicate(String script, Object... objects) {
    super();
    this.script = script;
  }

  public boolean evaluate(Object arg0) {
    return false;
  }

  public static void main(String[] args) {
    //List<AirPlan> plans = CollectUtils.newArrayList(new AirPlan("apache"), new AirPlan("jian10"));
    //PropertyPredicate predicate = new PropertyPredicate("name=:name", "jian10");
  }
}
