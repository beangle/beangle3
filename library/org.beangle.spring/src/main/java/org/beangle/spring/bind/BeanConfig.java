/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

public class BeanConfig {

	private List<Definition> definitions = CollectUtils.newArrayList();

	private List<Definition> lastAdded;

	public final static class Definition {
		public String beanName;
		public final Class<?> clazz;
		public String scope;

		public Definition(String beanName, Class<?> clazz, String scope) {
			super();
			this.beanName = beanName;
			this.clazz = clazz;
			if (null == scope) {
				this.scope = AbstractBeanDefinition.SCOPE_SINGLETON;
			} else {
				this.scope = scope;
			}
		}
	}

	public final static class DefinitionBinder {
		private BeanConfig config;
		private Scope scope = Scope.SINGLETON;
		private boolean useShortName;

		public DefinitionBinder(BeanConfig config, Class<?>... classes) {
			super();
			this.config = config;
			bind(classes);
		}

		public DefinitionBinder(BeanConfig config, boolean useShortName) {
			super();
			this.config = config;
			this.useShortName = useShortName;
		}

		public DefinitionBinder(BeanConfig config, Scope scope) {
			super();
			this.config = config;
			this.scope = scope;
		}

		public DefinitionBinder shortName() {
			return shortName(true);
		}
		
		public DefinitionBinder shortName(boolean b) {
			useShortName = b;
			if (null != config.lastAdded) {
				for (Definition def : config.lastAdded) {
					def.beanName = getBeanName(def.clazz);
				}
			}
			return this;
		}

		public DefinitionBinder in(Scope scope) {
			this.scope = scope;
			if (null != config.lastAdded) {
				for (Definition def : config.lastAdded) {
					def.scope = scope.toString();
				}
			}
			return this;
		}

		public DefinitionBinder bind(Class<?>... classes) {
			config.lastAdded = CollectUtils.newArrayList();
			for (Class<?> clazz : classes) {
				Definition def = build(clazz);
				config.add(def);
				config.lastAdded.add(def);
			}
			return this;
		}

		public DefinitionBinder bind(String name, Class<?> clazz) {
			config.lastAdded = CollectUtils.newArrayList();
			Definition def = new Definition(name, clazz, scope.toString());
			config.add(def);
			config.lastAdded.add(def);
			return this;
		}

		private String getBeanName(Class<?> clazz) {
			String className = clazz.getName();
			if (useShortName) className = StringUtils.uncapitalize(StringUtils.substringAfterLast(className,
					"."));
			return className;
		}

		private Definition build(Class<?> clazz) {
			return new Definition(getBeanName(clazz), clazz, scope.toString());
		}
	}

	public DefinitionBinder bind(String beanName, Class<?> clazz) {
		DefinitionBinder binder = new DefinitionBinder(this);
		binder.bind(beanName, clazz);
		return binder;
	}

	public DefinitionBinder bind(Class<?>... classes) {
		return new DefinitionBinder(this, classes);
	}

	protected void add(Definition def) {
		definitions.add(def);
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}

}
