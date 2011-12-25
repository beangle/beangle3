/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.config;

import static org.apache.commons.lang.StringUtils.substringBeforeLast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsException;
import org.beangle.collection.CollectUtils;
import org.beangle.struts2.convention.factory.BeanNameFinder;
import org.beangle.struts2.convention.factory.DefaultBeanNameFinder;
import org.beangle.struts2.convention.factory.SpringBeanNameFinder;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.convention.route.ActionBuilder;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.spring.SpringObjectFactory;
import com.opensymphony.xwork2.util.classloader.ReloadingClassLoader;
import com.opensymphony.xwork2.util.finder.ClassFinder;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.finder.Test;

/**
 * <p>
 * This class implements the ActionConfigBuilder interface.
 * </p>
 */
public class SmartActionConfigBuilder implements ActionConfigBuilder {
	private static final Logger logger = LoggerFactory.getLogger(SmartActionConfigBuilder.class);
	private final Configuration configuration;
	private final ObjectFactory objectFactory;
	private final String defaultParentPackage;

	private String[] actionPackages = null;
	private String actionSuffix = "Action";
	private boolean checkImplementsAction = true;
	private boolean devMode = false;

	private ReloadingClassLoader reloadingClassLoader;
	private BeanNameFinder beanNameFinder = new DefaultBeanNameFinder();

	@Inject
	protected ViewMapper viewMapper;

	@Inject
	protected ProfileService profileService;

	@Inject
	protected ActionBuilder actionBuilder;

	@Inject
	public SmartActionConfigBuilder(Configuration configuration, Container container,
			ObjectFactory objectFactory) {
		this.configuration = configuration;
		this.objectFactory = objectFactory;
		this.defaultParentPackage = "beangle";
		if (objectFactory instanceof SpringObjectFactory) {
			beanNameFinder = new SpringBeanNameFinder();
			SpringObjectFactory sf = (SpringObjectFactory) objectFactory;
			sf.autoWireBean(beanNameFinder);
		}
	}

	protected void initReloadClassLoader() {
		if (isReloadEnabled() && reloadingClassLoader == null) reloadingClassLoader = new ReloadingClassLoader(
				getClassLoader());
	}

	protected ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public void buildActionConfigs() {
		long start = System.currentTimeMillis();
		logger.info("Action scan starting....");
		List<String> packages = CollectUtils.newArrayList();
		for (Profile profile : actionBuilder.getProfileService().getProfiles()) {
			if (profile.isActionScan()) {
				packages.add(profile.getActionPattern());
			}
		}
		if (packages.isEmpty()) { return; }
		actionPackages = new String[packages.size()];
		packages.toArray(actionPackages);
		// setup reload class loader based on dev settings
		initReloadClassLoader();
		@SuppressWarnings("rawtypes")
		Set<Class> classes = findActions();
		int newActions = buildConfiguration(classes);
		logger.info("Action scan completely,create {} action in {} ms", newActions,
				System.currentTimeMillis() - start);
	}

	protected ClassLoaderInterface getClassLoaderInterface() {
		if (isReloadEnabled()) return new ClassLoaderInterfaceDelegate(reloadingClassLoader);
		else {
			ClassLoaderInterface classLoaderInterface = null;
			ActionContext ctx = ActionContext.getContext();
			if (ctx != null) classLoaderInterface = (ClassLoaderInterface) ctx
					.get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);
			return (ClassLoaderInterface) ObjectUtils.defaultIfNull(classLoaderInterface,
					new ClassLoaderInterfaceDelegate(getClassLoader()));
		}
	}

	protected boolean isReloadEnabled() {
		return devMode;
	}

	@SuppressWarnings("rawtypes")
	protected Set<Class> findActions() {
		Set<Class> classes = CollectUtils.newHashSet();
		try {
			@SuppressWarnings("serial")
			Set<String> jarProtocols = new HashSet<String>() {
				@Override
				public boolean contains(Object o) {
					return !ObjectUtils.equals(o, "file");
				}
			};
			ClassFinder finder = new ClassFinder(getClassLoaderInterface(), buildUrls(), false, jarProtocols);
			for (String packageName : actionPackages) {
				Test<ClassFinder.ClassInfo> test = getPackageFinderTest(packageName);
				classes.addAll(finder.findClasses(test));
			}
		} catch (Exception ex) {
			logger.error("Unable to scan named packages", ex);
		}
		return classes;
	}

	protected Test<ClassFinder.ClassInfo> getPackageFinderTest(final String packageName) {
		// so "my.package" does not match "my.package2.test"
		// final String strictPackageName = packageName + ".";
		return new Test<ClassFinder.ClassInfo>() {
			public boolean test(ClassFinder.ClassInfo classInfo) {
				String classPackageName = classInfo.getPackageName();
				boolean inPackage = Profile.isInPackage(packageName, classPackageName);
				boolean nameMatches = classInfo.getName().endsWith(actionSuffix);
				try {
					return inPackage
							&& (nameMatches || (checkImplementsAction && com.opensymphony.xwork2.Action.class
									.isAssignableFrom(classInfo.get())));
				} catch (ClassNotFoundException ex) {
					logger.error("Unable to load class " + classInfo.getName(), ex);
					return false;
				}
			}
		};
	}

	private Set<URL> buildUrls() {
		Set<URL> urls = CollectUtils.newHashSet();
		Enumeration<URL> em;
		ClassLoaderInterface classloader = getClassLoaderInterface();
		try {
			em = classloader.getResources("struts-plugin.xml");
			while (em.hasMoreElements()) {
				URL url = em.nextElement();
				urls.add(new URL(substringBeforeLast(url.toExternalForm(), "struts-plugin.xml")));
			}
			em = classloader.getResources("struts.xml");
			while (em.hasMoreElements()) {
				URL url = em.nextElement();
				urls.add(new URL(substringBeforeLast(url.toExternalForm(), "struts.xml")));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		logger.info("build action from {}", urls);
		return urls;
	}

	@SuppressWarnings("rawtypes")
	protected int buildConfiguration(Set<Class> classes) {
		Map<String, PackageConfig.Builder> packageConfigs = new HashMap<String, PackageConfig.Builder>();
		int createCount = 0;
		for (Class<?> actionClass : classes) {
			// Skip classes that can't be instantiated
			if (cannotInstantiate(actionClass)) {
				logger.debug("Class {} did not pass the instantiation test and will be ignored",
						actionClass.getName());
				continue;
			}
			try {
				objectFactory.getClassInstance(actionClass.getName());
			} catch (ClassNotFoundException e) {
				logger.error("Object Factory was unable to load class {}", actionClass.getName());
				throw new StrutsException("Object Factory was unable to load class " + actionClass.getName(),
						e);
			}
			String[] beanNames = beanNameFinder.getBeanNames(actionClass);
			switch (beanNames.length) {
			case 0:
				logger.warn("Cannot find bean definition for {}.", actionClass);
				break;
			case 1:
				Profile profile = actionBuilder.getProfileService().getProfile(actionClass.getName());
				Action action = actionBuilder.build(actionClass.getName());
				PackageConfig.Builder packageConfig = getPackageConfig(profile, packageConfigs, action,
						actionClass);
				if (createActionConfig(packageConfig, action, actionClass, beanNames[0])) {
					createCount++;
				}
				break;
			default:
				logger.warn("Duplicated action definition for {}", actionClass);
			}
		}
		createCount += buildIndexActions(packageConfigs);
		// Add the new actions to the configuration
		Set<String> packageNames = packageConfigs.keySet();
		for (String packageName : packageNames) {
			configuration.removePackageConfig(packageName);
			configuration.addPackageConfig(packageName, packageConfigs.get(packageName).build());
		}
		return createCount;
	}

	/**
	 * Interfaces, enums, annotations, and abstract classes cannot be
	 * instantiated.
	 * 
	 * @param actionClass
	 *            class to check
	 * @return returns true if the class cannot be instantiated or should be
	 *         ignored
	 */
	protected boolean cannotInstantiate(Class<?> actionClass) {
		return actionClass.isAnnotation() || actionClass.isInterface() || actionClass.isEnum()
				|| (actionClass.getModifiers() & Modifier.ABSTRACT) != 0 || actionClass.isAnonymousClass();
	}

	protected boolean createActionConfig(PackageConfig.Builder pkgCfg, Action action, Class<?> actionClass,
			String beanName) {
		ActionConfig.Builder actionConfig = new ActionConfig.Builder(pkgCfg.getName(), action.getName(),
				beanName);
		actionConfig.methodName(action.getMethod());
		String actionName = action.getName();
		// check action exists on that package (from XML config probably)
		PackageConfig existingPkg = configuration.getPackageConfig(pkgCfg.getName());
		boolean create = true;
		if (existingPkg != null) {
			ActionConfig existed = existingPkg.getActionConfigs().get(actionName);
			create = (null == existed);
		}
		if (create) {
			actionConfig.addResultConfigs(buildResultConfigs(actionClass));
			pkgCfg.addActionConfig(actionName, actionConfig.build());
			logger.debug("Add {}/{} for {} in {}", new Object[] { pkgCfg.getNamespace(), actionName,
					actionClass.getName(), pkgCfg.getName() });
		}
		return create;
	}

	protected boolean shouldGenerateResult(Method m) {
		if (String.class.equals(m.getReturnType()) && m.getParameterTypes().length == 0
				&& Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
			String name = m.getName();
			if (StringUtils.containsIgnoreCase(name, "save")
					|| StringUtils.containsIgnoreCase(name, "remove")
					|| StringUtils.containsIgnoreCase(name, "export")
					|| StringUtils.containsIgnoreCase(name, "import")
					|| StringUtils.containsIgnoreCase(name, "execute")
					|| StringUtils.containsIgnoreCase(name, "toString")) { return false; }
			return true;
		}
		return false;
	}

	protected List<ResultConfig> buildResultConfigs(Class<?> clazz) {
		List<ResultConfig> configs = CollectUtils.newArrayList();
		String extention = profileService.getProfile(clazz.getName()).getViewExtension();
		if (!extention.endsWith("ftl")) return configs;
		ResultTypeConfig resultTypeConfig = configuration.getPackageConfig("struts-default")
				.getAllResultTypeConfigs().get("freemarker");
		for (Method m : clazz.getMethods()) {
			if (String.class.equals(m.getReturnType()) && m.getParameterTypes().length == 0
					&& Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
				String name = m.getName();
				if (shouldGenerateResult(m)) {
					StringBuilder buf = new StringBuilder();
					buf.append(viewMapper.getViewPath(clazz.getName(), name, name));
					buf.append('.');
					buf.append(extention);
					configs.add(new ResultConfig.Builder(name, resultTypeConfig.getClassName()).addParam(
							resultTypeConfig.getDefaultResultParam(), buf.toString()).build());
				}
			}
		}
		return configs;
	}

	protected PackageConfig.Builder getPackageConfig(Profile profile,
			final Map<String, PackageConfig.Builder> packageConfigs, Action action, final Class<?> actionClass) {
		// 循环查找父包
		String actionPkg = actionClass.getPackage().getName();
		PackageConfig parentPkg = null;
		while (StringUtils.contains(actionPkg, '.')) {
			parentPkg = configuration.getPackageConfig(actionPkg);
			if (null != parentPkg) {
				break;
			} else {
				actionPkg = StringUtils.substringBeforeLast(actionPkg, ".");
			}
		}
		if (null == parentPkg) {
			actionPkg = defaultParentPackage;
			parentPkg = configuration.getPackageConfig(actionPkg);
		}
		if (parentPkg == null) { throw new ConfigurationException("Unable to locate parent package ["
				+ actionClass.getPackage().getName() + "]"); }
		String actionPackage = actionClass.getPackage().getName();
		PackageConfig.Builder pkgConfig = packageConfigs.get(actionPackage);
		if (pkgConfig == null) {
			PackageConfig myPkg = configuration.getPackageConfig(actionPackage);
			if (null != myPkg) {
				pkgConfig = new PackageConfig.Builder(myPkg);
			} else {
				pkgConfig = new PackageConfig.Builder(actionPackage).namespace(action.getNamespace())
						.addParent(parentPkg);
				logger.debug("Created package config named {} with a namespace {}", actionPackage,
						action.getNamespace());
			}
			packageConfigs.put(actionPackage, pkgConfig);
		}
		return pkgConfig;
	}

	/**
	 * Determine all the index handling actions and results based on this logic:
	 * 1. Loop over all the namespaces such as /foo and see if it has an action
	 * named index 2. If an action doesn't exists in the parent namespace of the
	 * same name, create an action in the parent namespace of the same name as
	 * the namespace that points to the index action in the namespace. e.g. /foo
	 * -> /foo/index 3. Create the action in the namespace for empty string if
	 * it doesn't exist. e.g. /foo/ the action is "" and the namespace is /foo
	 * 
	 * @param packageConfigs
	 *            Used to store the actions.
	 */
	protected int buildIndexActions(Map<String, PackageConfig.Builder> packageConfigs) {
		int createCount = 0;
		Map<String, PackageConfig.Builder> byNamespace = new HashMap<String, PackageConfig.Builder>();
		Collection<PackageConfig.Builder> values = packageConfigs.values();
		for (PackageConfig.Builder packageConfig : values) {
			byNamespace.put(packageConfig.getNamespace(), packageConfig);
		}
		Set<String> namespaces = byNamespace.keySet();
		for (String namespace : namespaces) {
			// First see if the namespace has an index action
			PackageConfig.Builder pkgConfig = byNamespace.get(namespace);
			ActionConfig indexActionConfig = pkgConfig.build().getAllActionConfigs().get("index");
			if (indexActionConfig == null) {
				continue;
			}
			if (pkgConfig.build().getAllActionConfigs().get("") == null) {
				logger.debug("Creating index ActionConfig with an action name of [] for the action class {}",
						indexActionConfig.getClassName());
				pkgConfig.addActionConfig("", indexActionConfig);
				createCount++;
			}
		}
		return createCount;
	}

	public void destroy() {
		// loadedFileUrls.clear();
	}

	public boolean needsReload() {
		return devMode;
	}

	public ActionBuilder getActionBuilder() {
		return actionBuilder;
	}

	public void setActionBuilder(ActionBuilder actionNameBuilder) {
		this.actionBuilder = actionNameBuilder;
	}

	public void setViewMapper(ViewMapper viewMapper) {
		this.viewMapper = viewMapper;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

}
