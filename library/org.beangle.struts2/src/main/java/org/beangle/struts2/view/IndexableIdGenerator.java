package org.beangle.struts2.view;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.lang.StrUtils;

/**
 * 基于每种ui一个序列的id产生器
 * 
 * @author chaostone
 */
public class IndexableIdGenerator implements UIIdGenerator {

	private final String seed;

	private Map<Class<?>, UIIndex> uiIndexes = CollectUtils.newHashMap();

	public IndexableIdGenerator(int seed) {
		this.seed = String.valueOf(seed);
	}

	public String generate(Class<?> clazz) {
		UIIndex index = uiIndexes.get(clazz);
		if (null == index) {
			index = new UIIndex(StringUtils.uncapitalize(clazz.getSimpleName()));
			uiIndexes.put(clazz, index);
		}
		return index.genId(seed);
	}

	private static class UIIndex {
		int index = 0;
		final String name;

		UIIndex(String name) {
			super();
			this.name = name;
		}

		public String genId(String seed) {
			index++;
			return StrUtils.concat(name,seed, String.valueOf(index));
		}
	}
}
