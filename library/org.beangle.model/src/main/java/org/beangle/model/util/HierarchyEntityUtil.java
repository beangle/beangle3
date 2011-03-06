/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.HierarchyEntity;

public class HierarchyEntityUtil {

	public Map<HierarchyEntity<?>, String> sort(List<? extends HierarchyEntity<?>> datas) {
		return sort(datas, null);
	}

	public Map<HierarchyEntity<?>, String> sort(List<? extends HierarchyEntity<?>> datas, String property) {
		final Map<HierarchyEntity<?>, String> sortedMap = CollectUtils.newHashMap();
		for (HierarchyEntity<?> de : datas) {
			String myId = null;
			if (null == property) {
				myId = String.valueOf(de.getIdentifier());
			} else {
				try {
					myId = String.valueOf(PropertyUtils.getProperty(de, property));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			myId = myId + "_";
			if (null != de.getParent() && sortedMap.containsKey(de.getParent())) {
				myId = String.valueOf(sortedMap.get(de.getParent()) + myId);
				if (!myId.endsWith("_")) {
					myId += "_";
				}
			}
			updatedTagFor(myId, de, sortedMap);
			sortedMap.put(de, myId);
		}
		for (HierarchyEntity<?> de : datas) {
			String tag = sortedMap.get(de);
			if (!tag.endsWith("_")) {
				String newTag = tag += "_";
				sortedMap.put(de, newTag);
			}
		}
		Collections.sort(datas, new Comparator<HierarchyEntity<?>>() {
			public int compare(HierarchyEntity<?> arg0, HierarchyEntity<?> arg1) {
				String tag0 = sortedMap.get(arg0);
				String tag1 = sortedMap.get(arg1);
				return tag0.compareTo(tag1);
			}
		});
		return sortedMap;
	}

	private void updatedTagFor(String prefix, HierarchyEntity<?> root,
			Map<HierarchyEntity<?>, String> sortedMap) {
		for (HierarchyEntity<?> child : root.getChildren()) {
			if (sortedMap.containsKey(child)) {
				sortedMap.put(child, prefix + sortedMap.get(child));
				updatedTagFor(prefix, child, sortedMap);
			}
		}
	}
}
