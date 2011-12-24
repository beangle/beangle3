/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev.helper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ResolverUtil;
import com.opensymphony.xwork2.util.reflection.ReflectionContextFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionProvider;
import com.opensymphony.xwork2.validator.ActionValidatorManager;

/**
 * @author chaostone
 * @version $Id: S2ConfigurationHelper.java Dec 24, 2011 5:07:49 PM chaostone $
 */
public class S2ConfigurationHelper {

	private Configuration configuration;
	private Container container;
	private ObjectFactory objectFactory;
	private ReflectionProvider reflectionProvider;
	private ReflectionContextFactory reflectionContextFactory;
	private ActionValidatorManager actionValidatorManager;

	@Inject
	public void setConfiguration(Configuration config) {
		this.configuration = config;
	}

	@Inject
	public void setContainer(Container container) {
		this.container = container;
	}

	public Set<String> getNamespaces() {
		Set<String> namespaces = Collections.emptySet();
		Map<String, ?> allActionConfigs = configuration.getRuntimeConfiguration().getActionConfigs();
		if (allActionConfigs != null) {
			namespaces = allActionConfigs.keySet();
		}
		return namespaces;
	}

	public Set<String> getActionNames(String namespace) {
		Set<String> actionNames = Collections.emptySet();
		Map<String, Map<String, ActionConfig>> allActionConfigs = configuration.getRuntimeConfiguration()
				.getActionConfigs();
		if (allActionConfigs != null) {
			Map<String, ?> actionMappings = allActionConfigs.get(namespace);
			if (actionMappings != null) {
				actionNames = actionMappings.keySet();
			}
		}
		return actionNames;
	}

	public ActionConfig getActionConfig(String namespace, String actionName) {
		ActionConfig config = null;
		Map<String, Map<String, ActionConfig>> allActionConfigs = configuration.getRuntimeConfiguration()
				.getActionConfigs();
		if (allActionConfigs != null) {
			Map<String, ActionConfig> actionMappings = allActionConfigs.get(namespace);
			if (actionMappings != null) config = actionMappings.get(actionName);
		}
		return config;
	}

	public List<Properties> getJarProperties() {
		@SuppressWarnings("rawtypes")
		ResolverUtil<?> resolver = new ResolverUtil();
		List<Properties> poms = new ArrayList<Properties>();
		resolver.findNamedResource("pom.properties", "META-INF/maven");
		Set<URL> urls = resolver.getResources();
		for (URL url : urls) {
			Properties p = new Properties();
			try {
				p.load(url.openStream());
				poms.add(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return poms;
	}

	public Container getContainer() {
		return container;
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public ReflectionProvider getReflectionProvider() {
		return reflectionProvider;
	}

	@Inject
	public void setReflectionProvider(ReflectionProvider reflectionProvider) {
		this.reflectionProvider = reflectionProvider;
	}

	@Inject
	public void setReflectionContextFactory(ReflectionContextFactory reflectionContextFactory) {
		this.reflectionContextFactory = reflectionContextFactory;
	}

	public ReflectionContextFactory getReflectionContextFactory() {
		return reflectionContextFactory;
	}

	public ActionValidatorManager getActionValidatorManager() {
		return actionValidatorManager;
	}
	@Inject
	public void setActionValidatorManager(ActionValidatorManager actionValidatorManager) {
		this.actionValidatorManager = actionValidatorManager;
	}

}
