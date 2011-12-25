/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev.struts2.action;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.components.UrlRenderer;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.apache.struts2.util.ClassLoaderUtils;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.velocity.VelocityManager;
import org.beangle.ems.dev.struts2.helper.S2ConfigurationHelper;
import org.beangle.struts2.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.conversion.ObjectTypeDeterminer;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.util.reflection.ReflectionContextFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionException;
import com.opensymphony.xwork2.validator.Validator;

/**
 * @author chaostone
 * @version $Id: Struts2Action.java Dec 24, 2011 4:12:37 PM chaostone $
 */
public class ConfigBrowserAction extends BaseAction {

	protected S2ConfigurationHelper configHelper;

	public String index() {
		Set<String> namespaces = configHelper.getNamespaces();
		if (namespaces.size() == 0) {
			addActionError("There are no namespaces in this configuration");
			return ERROR;
		}
		String namespace = get("namespace");
		if (namespace == null) namespace = "";

		Set<String> actionNames = new TreeSet<String>(configHelper.getActionNames(namespace));
		put("namespace", namespace);
		put("actionNames", actionNames);
		put("namespaces", namespaces);
		return forward();
	}

	public String actions() {
		String namespace = get("namespace");
		if (namespace == null) namespace = "";
		Set<String> actionNames = new TreeSet<String>(configHelper.getActionNames(namespace));
		put("namespace", namespace);
		put("actionNames", actionNames);
		return forward();
	}

	public String config() {
		String namespace = get("namespace");
		String actionName = get("actionName");
		ActionConfig config = configHelper.getActionConfig(namespace, actionName);
		put("actionNames", new TreeSet<String>(configHelper.getActionNames(namespace)));
		try {
			Class<?> clazz = configHelper.getObjectFactory().getClassInstance(config.getClassName());
			put("properties", configHelper.getReflectionProvider().getPropertyDescriptors(clazz));
		} catch (Exception e) {
			logger.error("Unable to get properties for action " + actionName, e);
			addActionError("Unable to retrieve action properties: " + e.toString());
		}
		String extension = null;
		for (String key : configHelper.getContainer().getInstanceNames(String.class)) {
			if (key.equals(StrutsConstants.STRUTS_ACTION_EXTENSION)) {
				extension = configHelper.getContainer().getInstance(String.class, key);
				break;
			}
		}
		if (extension == null) { return "action"; }
		if (extension.indexOf(",") > -1) extension = extension.substring(0, extension.indexOf(","));
		put("detailView", getOrElse("detailView", "results"));
		put("extension", extension);
		put("config", config);
		put("namespace", namespace);
		put("actionName", actionName);
		return forward();
	}

	public String beans() {
		Container container = configHelper.getContainer();
		Set<Binding> bindings = new TreeSet<Binding>();
		addBinding(bindings, container, ObjectFactory.class, StrutsConstants.STRUTS_OBJECTFACTORY);
		addBinding(bindings, container, XWorkConverter.class, StrutsConstants.STRUTS_XWORKCONVERTER);
		addBinding(bindings, container, TextProvider.class, StrutsConstants.STRUTS_XWORKTEXTPROVIDER);
		addBinding(bindings, container, ActionProxyFactory.class, StrutsConstants.STRUTS_ACTIONPROXYFACTORY);
		addBinding(bindings, container, ObjectTypeDeterminer.class,
				StrutsConstants.STRUTS_OBJECTTYPEDETERMINER);
		addBinding(bindings, container, ActionMapper.class, StrutsConstants.STRUTS_MAPPER_CLASS);
		addBinding(bindings, container, MultiPartRequest.class, StrutsConstants.STRUTS_MULTIPART_PARSER);
		addBinding(bindings, container, FreemarkerManager.class,
				StrutsConstants.STRUTS_FREEMARKER_MANAGER_CLASSNAME);
		addBinding(bindings, container, VelocityManager.class,
				StrutsConstants.STRUTS_VELOCITY_MANAGER_CLASSNAME);
		addBinding(bindings, container, UrlRenderer.class, StrutsConstants.STRUTS_URL_RENDERER);
		put("beans", bindings);
		return forward();
	}

	private void addBinding(Set<Binding> bindings, Container container, Class<?> type, String constName) {
		String chosenName = container.getInstance(String.class, constName);
		if (chosenName == null) {
			chosenName = "struts";
		}
		Set<String> names = container.getInstanceNames(type);
		if (null != names) {
			if (!names.contains(chosenName)) {
				bindings.add(new Binding(type.getName(), getInstanceClassName(container, type, "default"),
						chosenName, constName, true));
			}
			for (String name : names) {
				if (!"default".equals(name)) bindings.add(new Binding(type.getName(), getInstanceClassName(
						container, type, name), name, constName, name.equals(chosenName)));
			}
		}
	}

	private String getInstanceClassName(Container container, Class<?> type, String name) {
		String instName = "Class unable to be loaded";
		try {
			Object inst = container.getInstance(type, name);
			instName = inst.getClass().getName();
		} catch (Exception ex) {
			// Ignoring beans unable to be loaded
		}
		return instName;
	}

	public String consts() {
		Map<String, String> consts = new HashMap<String, String>();
		for (String key : configHelper.getContainer().getInstanceNames(String.class)) {
			consts.put(key, configHelper.getContainer().getInstance(String.class, key));
		}
		put("consts", consts);
		return forward();
	}

	public String jars() throws IOException {
		put("jarPoms", configHelper.getJarProperties());
		put("pluginsLoaded", ClassLoaderUtil.getResources("struts-plugin.xml", ConfigBrowserAction.class, false));
		return forward();
	}

	@Inject
	public void setConfigHelper(S2ConfigurationHelper configHelper) {
		this.configHelper = configHelper;
	}

	public class Binding implements Comparable<Binding> {
		private String type;
		private String impl;
		private String alias;
		private String constant;
		private boolean isDefault;

		public Binding(String type, String impl, String alias, String constant, boolean def) {
			this.type = type;
			this.impl = impl;
			this.alias = alias;
			this.constant = constant;
			this.isDefault = def;
		}

		public String getType() {
			return type;
		}

		public String getImpl() {
			return impl;
		}

		public String getAlias() {
			return alias;
		}

		public String getConstant() {
			return constant;
		}

		public boolean isDefault() {
			return isDefault;
		}

		public int compareTo(Binding b2) {
			int ret = 0;
			if (isDefault) {
				ret = -1;
			} else if (b2.isDefault()) {
				ret = 1;
			} else {
				ret = alias.compareTo(b2.getAlias());
			}
			return ret;
		}
	}

	public String stripPackage(String clazz) {
		return clazz.substring(clazz.lastIndexOf('.') + 1);
	}

	public String stripPackage(Class<?> clazz) {
		return clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
	}

	private Class<?> getClassInstance(String clazz) {
		try {
			return ClassLoaderUtils.loadClass(clazz, ActionContext.getContext().getClass());
		} catch (Exception e) {
			logger.error("Class '" + clazz + "' not found...", e);
		}
		return null;
	}

	public String validators() {
		ReflectionContextFactory reflectionContextFactory = configHelper.getReflectionContextFactory();
		Class<?> clazz = getClassInstance(get("clazz"));
		@SuppressWarnings("rawtypes")
		List<Validator> validators = Collections.emptyList();
		if (clazz != null) validators = configHelper.getActionValidatorManager().getValidators(clazz,
				get("context"));

		Set<PropertyInfo> properties = new TreeSet<PropertyInfo>();
		put("properties", properties);
		if (validators.isEmpty()) { return forward(); }
		int selected = getInt("selected");
		Validator<?> validator = validators.get(selected);
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> context = reflectionContextFactory.createDefaultContext(validator);
			BeanInfo beanInfoFrom = null;
			try {
				beanInfoFrom = Introspector.getBeanInfo(validator.getClass(), Object.class);
			} catch (IntrospectionException e) {
				logger.error("An error occurred", e);
				addActionError("An error occurred while introspecting a validator of type "
						+ validator.getClass().getName());
				return ERROR;
			}

			PropertyDescriptor[] pds = beanInfoFrom.getPropertyDescriptors();

			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				String name = pd.getName();
				Object value = null;
				if (pd.getReadMethod() == null) {
					value = "No read method for property";
				} else {
					try {
						value = configHelper.getReflectionProvider().getValue(name, context, validator);
					} catch (ReflectionException e) {
						addActionError("Caught exception while getting property value for '" + name
								+ "' on validator of type " + validator.getClass().getName());
					}
				}
				properties.add(new PropertyInfo(name, pd.getPropertyType(), value));
			}
		} catch (Exception e) {
			logger.warn("Unable to retrieve properties.", e);
			addActionError("Unable to retrieve properties: " + e.toString());
		}
		return forward();
	}

	public static class PropertyInfo implements Comparable<PropertyInfo> {
		private final String name;
		private final Class<?> type;
		private final Object value;

		public PropertyInfo(String name, Class<?> type, Object value) {
			if (name == null) { throw new IllegalArgumentException("Name must not be null"); }
			if (type == null) { throw new IllegalArgumentException("Type must not be null"); }
			this.name = name;
			this.type = type;
			this.value = value;
		}

		public Class<?> getType() {
			return type;
		}

		public Object getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof PropertyInfo)) return false;

			final PropertyInfo propertyInfo = (PropertyInfo) o;
			if (!name.equals(propertyInfo.name)) return false;
			if (!type.equals(propertyInfo.type)) return false;
			if (value != null ? !value.equals(propertyInfo.value) : propertyInfo.value != null) return false;
			return true;
		}

		public int hashCode() {
			int result;
			result = name.hashCode();
			result = 29 * result + type.hashCode();
			result = 29 * result + (value != null ? value.hashCode() : 0);
			return result;
		}

		public int compareTo(PropertyInfo other) {
			return this.name.compareTo(other.name);
		}
	}
}
