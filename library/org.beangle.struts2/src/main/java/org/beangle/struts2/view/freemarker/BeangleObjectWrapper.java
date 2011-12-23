/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freemarker.core.CollectionAndSequence;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.util.ModelFactory;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * BeangleObjectWrapper不同于StrutsBeanWrapper，他扩展了DefaultObjectWrapper。提供了<br>
 * 1)缺省的对象包装<br>
 * 2)可以在set等没有顺序的模型使用?seq_contains等方法
 * 3)恢复了在Map上的get方法(被DefaultObjectWrapper覆盖掉的)<br>
 * 4)提供了map上的values方法(等同于StrutsBeanWrapper的功能)
 */
public class BeangleObjectWrapper extends DefaultObjectWrapper {
	private boolean altMapWrapper;

	public BeangleObjectWrapper(boolean altMapWrapper) {
		this.altMapWrapper = altMapWrapper;
	}

	/**
	 * 特殊包装set和map
	 */
	public TemplateModel wrap(Object obj) throws TemplateModelException {
		if (obj == null) { return super.wrap(null); }
		if (obj instanceof List<?>) { return new CollectionModel((Collection<?>) obj, this); }
		// 使得set等集合可以排序
		if (obj instanceof Collection<?>) { return new SimpleSequence((Collection<?>) obj, this); }
		if (obj instanceof Map<?, ?>) {
			if (altMapWrapper) {
				return new FriendlyMapModel((Map<?, ?>) obj, this);
			} else {
				return new MapModel((Map<?, ?>) obj, this);
			}
		}
		return super.wrap(obj);
	}

	// attempt to get the best of both the SimpleMapModel and the MapModel
	// of FM.
	@SuppressWarnings("rawtypes")
	protected ModelFactory getModelFactory(Class clazz) {
		if (altMapWrapper && Map.class.isAssignableFrom(clazz)) { return FriendlyMapModel.FACTORY; }
		return super.getModelFactory(clazz);
	}

	/**
	 * Attempting to get the best of both worlds of FM's MapModel and
	 * simplemapmodel, by reimplementing the isEmpty(), keySet() and values()
	 * methods. ?keys and ?values built-ins are thus available, just as well as
	 * plain Map methods.
	 */
	private final static class FriendlyMapModel extends MapModel implements TemplateHashModelEx,
			TemplateMethodModelEx, AdapterTemplateModel {
		static final ModelFactory FACTORY = new ModelFactory() {
			public TemplateModel create(Object object, ObjectWrapper wrapper) {
				return new FriendlyMapModel((Map<?, ?>) object, (BeangleObjectWrapper) wrapper);
			}
		};

		public FriendlyMapModel(Map<?, ?> map, BeangleObjectWrapper wrapper) {
			super(map, wrapper);
		}

		// Struts2将父类的&& super.isEmpty()省去了，原因不知
		public boolean isEmpty() {
			return ((Map<?, ?>) object).isEmpty();
		}

		// 此处实现与MapModel不同，MapModel中复制了一个集合
		// 影响了?keySet,?size方法
		protected Set<?> keySet() {
			return ((Map<?, ?>) object).keySet();
		}

		// add feature
		public TemplateCollectionModel values() {
			return new CollectionAndSequence(new SimpleSequence(((Map<?, ?>) object).values(), wrapper));
		}
	}
}
