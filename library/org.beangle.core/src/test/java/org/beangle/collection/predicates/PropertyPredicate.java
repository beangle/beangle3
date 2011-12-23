package org.beangle.collection.predicates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.beangle.collection.CollectUtils;

public class PropertyPredicate implements Predicate {

	String script;
	Map params = new HashMap();

	private PropertyPredicate(String script, Object... objects) {
		super();
		this.script = script;
	}

	public boolean evaluate(Object arg0) {
		return false;
	}

	public static void main(String[] args) {
		List<AirPlan> plans = CollectUtils.newArrayList(new AirPlan("apache"), new AirPlan("jian10"));
		PropertyPredicate predicate = new PropertyPredicate("name=:name", "jian10");
	}
}
