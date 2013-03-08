/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.struts2.convention.config.ActionFinder.ActionTest;
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
import com.opensymphony.xwork2.config.PackageProvider;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.classloader.ReloadingClassLoader;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;

/**
 * <p>
 * This class is a configuration provider for the XWork configuration system. This is really the
 * only way to truly handle loading of the packages, actions and results correctly.
 * </p>
 */
public class ConventionPackageProvider implements PackageProvider {
  private static final Logger logger = LoggerFactory.getLogger(ConventionPackageProvider.class);
  private final Configuration configuration;
  private final String defaultParentPackage;

  private List<String> actionPackages = CollectUtils.newArrayList();
  private String actionSuffix = "Action";
  private boolean devMode = false;

  private ReloadingClassLoader reloadingClassLoader;

  private ActionFinder actionFinder;

  @Inject
  protected ViewMapper viewMapper;

  @Inject
  protected ProfileService profileService;

  @Inject
  protected ActionBuilder actionBuilder;

  @Inject
  protected TextBundleRegistry registry;

  @Inject("beangle.i18n.resources")
  protected String defaultBundleNames;

  @Inject("beangle.i18n.reload")
  private String reloadBundles = "false";

  @Inject
  public ConventionPackageProvider(Configuration configuration, Container container,
      ObjectFactory objectFactory) throws Exception {
    this.configuration = configuration;
    this.defaultParentPackage = "beangle";
    actionFinder = (ActionFinder) objectFactory.buildBean(ContainerActionFinder.class,
        new HashMap<String, Object>(0));
  }

  protected void initReloadClassLoader() {
    if (isReloadEnabled() && reloadingClassLoader == null)
      reloadingClassLoader = new ReloadingClassLoader(getClassLoader());
  }

  protected ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  public void init(Configuration configuration) throws ConfigurationException {
  }

  public void loadPackages() throws ConfigurationException {
    registry.addDefaults(Strings.split(defaultBundleNames));
    registry.setReloadBundles(Boolean.valueOf(reloadBundles));

    Stopwatch watch = new Stopwatch(true);
    for (Profile profile : actionBuilder.getProfileService().getProfiles()) {
      if (profile.isActionScan()) actionPackages.add(profile.getActionPattern());
    }
    if (actionPackages.isEmpty()) { return; }

    initReloadClassLoader();
    Map<String, PackageConfig.Builder> packageConfigs = new HashMap<String, PackageConfig.Builder>();
    int newActions = 0;
    Map<Class<?>, String> actionTypes = actionFinder.getActions(new ActionTest(actionSuffix, actionPackages));
    for (Map.Entry<Class<?>, String> entry : actionTypes.entrySet()) {
      Class<?> actionClass = entry.getKey();
      Profile profile = actionBuilder.getProfileService().getProfile(actionClass.getName());
      Action action = actionBuilder.build(actionClass.getName());
      PackageConfig.Builder packageConfig = getPackageConfig(profile, packageConfigs, action, actionClass);
      if (createActionConfig(packageConfig, action, actionClass, entry.getValue())) newActions++;
    }
    newActions += buildIndexActions(packageConfigs);
    // Add the new actions to the configuration
    Set<String> packageNames = packageConfigs.keySet();
    for (String packageName : packageNames) {
      configuration.removePackageConfig(packageName);
      configuration.addPackageConfig(packageName, packageConfigs.get(packageName).build());
    }
    logger.info("Action scan completed,create {} action in {}.", newActions, watch);
  }

  protected ClassLoaderInterface getClassLoaderInterface() {
    if (isReloadEnabled()) return new ClassLoaderInterfaceDelegate(reloadingClassLoader);
    else {
      ClassLoaderInterface classLoaderInterface = null;
      ActionContext ctx = ActionContext.getContext();
      if (ctx != null)
        classLoaderInterface = (ClassLoaderInterface) ctx.get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);
      return Objects.defaultIfNull(classLoaderInterface, new ClassLoaderInterfaceDelegate(getClassLoader()));
    }
  }

  protected boolean isReloadEnabled() {
    return devMode;
  }

  protected boolean createActionConfig(PackageConfig.Builder pkgCfg, Action action, Class<?> actionClass,
      String beanName) {
    ActionConfig.Builder actionConfig = new ActionConfig.Builder(pkgCfg.getName(), action.getName(), beanName);
    actionConfig.methodName(action.getMethod());
    String actionName = action.getName();
    // check action exists on that package (from XML config probably)
    PackageConfig existedPkg = configuration.getPackageConfig(pkgCfg.getName());
    boolean create = true;
    if (existedPkg != null) {
      ActionConfig existed = existedPkg.getActionConfigs().get(actionName);
      create = (null == existed);
    }
    if (create) {
      actionConfig.addResultConfigs(buildResultConfigs(actionClass));
      pkgCfg.addActionConfig(actionName, actionConfig.build());
      logger.debug("Add {}/{} for {} in {}",
          new Object[] { pkgCfg.getNamespace(), actionName, actionClass.getName(), pkgCfg.getName() });
    }
    return create;
  }

  protected boolean shouldGenerateResult(Method m) {
    if (String.class.equals(m.getReturnType()) && m.getParameterTypes().length == 0
        && Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
      String name = m.getName().toLowerCase();
      if (Strings.contains(name, "save") || Strings.contains(name, "remove")
          || Strings.contains(name, "export") || Strings.contains(name, "import")
          || Strings.contains(name, "execute") || Strings.contains(name, "toString")) { return false; }
      return true;
    }
    return false;
  }

  protected List<ResultConfig> buildResultConfigs(Class<?> clazz) {
    List<ResultConfig> configs = CollectUtils.newArrayList();
    if (null == profileService) return configs;
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
    while (Strings.contains(actionPkg, '.')) {
      parentPkg = configuration.getPackageConfig(actionPkg);
      if (null != parentPkg) {
        break;
      } else {
        actionPkg = Strings.substringBeforeLast(actionPkg, ".");
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
        pkgConfig = new PackageConfig.Builder(actionPackage).namespace(action.getNamespace()).addParent(
            parentPkg);
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
   *          Used to store the actions.
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

  public void setRegistry(TextBundleRegistry registry) {
    this.registry = registry;
  }

  public void setDefaultBundleNames(String defaultBundleNames) {
    this.defaultBundleNames = defaultBundleNames;
  }

}
