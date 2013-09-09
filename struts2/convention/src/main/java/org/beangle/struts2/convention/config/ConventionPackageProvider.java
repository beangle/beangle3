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

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.struts2.annotation.Result;
import org.beangle.struts2.annotation.Results;
import org.beangle.struts2.convention.config.ActionFinder.ActionTest;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.convention.route.ActionBuilder;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.ViewMapper;
import org.beangle.struts2.freemarker.TemplateFinder;
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
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.classloader.ReloadingClassLoader;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;

import freemarker.cache.TemplateLoader;

/**
 * <p>
 * This class is a configuration provider for the XWork configuration system. This is really the
 * only way to truly handle loading of the packages, actions and results correctly.
 * </p>
 */
public class ConventionPackageProvider implements PackageProvider {
  private static final Logger logger = LoggerFactory.getLogger(ConventionPackageProvider.class);

  private boolean devMode = false;

  private final Configuration configuration;

  private final FreemarkerManager freemarkerManager;

  private final ViewMapper viewMapper;

  private final ProfileService profileService;

  private final ActionBuilder actionBuilder;

  private final TextBundleRegistry registry;

  private ReloadingClassLoader reloadingClassLoader;

  private ActionFinder actionFinder;

  private List<String> actionPackages = CollectUtils.newArrayList();

  /** [url- > classname] */
  private Map<String, String> primaryMappings = CollectUtils.newHashMap();

  @Inject("beangle.convention.default.parent.package")
  private String defaultParentPackage;

  @Inject("beangle.convention.action.suffix")
  private String actionSuffix;

  @Inject("beangle.i18n.resources")
  private String defaultBundleNames;

  @Inject("beangle.i18n.reload")
  private String reloadBundles = "false";

  // Temperary use
  private TemplateFinder templateFinder;

  @Inject
  public ConventionPackageProvider(Configuration configuration, ObjectFactory objectFactory,
      FreemarkerManager freemarkerManager, ProfileService profileService, ActionBuilder actionBuilder,
      TextBundleRegistry registry, ViewMapper viewMapper) throws Exception {
    this.configuration = configuration;
    actionFinder = (ActionFinder) objectFactory.buildBean(ContainerActionFinder.class,
        new HashMap<String, Object>(0));
    this.freemarkerManager = freemarkerManager;
    this.profileService = profileService;
    this.actionBuilder = actionBuilder;
    this.registry = registry;
    this.viewMapper = viewMapper;
  }

  public void init(Configuration configuration) throws ConfigurationException {
    registry.addDefaults(Strings.split(defaultBundleNames));
    registry.setReloadBundles(Boolean.valueOf(reloadBundles));
    templateFinder = buildTemplateFinder();
    Properties properties = new Properties();
    URL url = ClassLoaders.getResource("struts.properties", getClass());
    if (null != url) {
      try {
        properties.load(url.openStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    for (Object key : properties.keySet()) {
      if (key.toString().startsWith("url.")) {
        primaryMappings.put(Strings.substringAfter(key.toString(), "url."),
            properties.getProperty(key.toString()));
      }
    }
  }

  public void loadPackages() throws ConfigurationException {
    Stopwatch watch = new Stopwatch(true);
    for (Profile profile : actionBuilder.getProfileService().getProfiles()) {
      if (profile.isActionScan()) actionPackages.add(profile.getActionPattern());
    }
    if (actionPackages.isEmpty()) { return; }

    initReloadClassLoader();
    Map<String, PackageConfig.Builder> packageConfigs = new HashMap<String, PackageConfig.Builder>();
    PackageConfig.Builder defaultPackageBuilder = new PackageConfig.Builder(
        configuration.getPackageConfig(defaultParentPackage));
    packageConfigs.put(defaultPackageBuilder.getName(), defaultPackageBuilder);
    int newActions = 0;
    int overrideActions = 0;
    Map<Class<?>, String> actionNames = actionFinder.getActions(new ActionTest(actionSuffix, actionPackages));
    Map<String, Class<?>> name2Actions = CollectUtils.newHashMap();
    Map<String, PackageConfig.Builder> name2Packages = CollectUtils.newHashMap();
    for (Map.Entry<Class<?>, String> entry : actionNames.entrySet()) {
      Class<?> actionClass = entry.getKey();
      Action action = actionBuilder.build(actionClass);
      // build key
      String key = null;
      if (action.getNamespace().equals("/")) key = action.getNamespace() + action.getName();
      else key = action.getNamespace() + "/" + action.getName();

      // check primary action class
      String primaryClassName = primaryMappings.get(key);
      if (null != primaryClassName && !primaryClassName.equals(actionClass.getName())) continue;

      // check action override relation
      PackageConfig.Builder pcb = null;
      Class<?> existAction = name2Actions.get(key);
      if (null == existAction) pcb = buildPackageConfig(actionClass, action, packageConfigs);
      else pcb = name2Packages.get(key);

      ActionConfig.Builder acb = null;
      String beanName = entry.getValue();
      if (null == existAction) {
        acb = createActionConfig(pcb, action, actionClass, beanName);
        if (null != acb) newActions++;
      } else {
        if (!actionClass.isAssignableFrom(existAction)) {
          acb = createActionConfig(pcb, action, actionClass, beanName);
          if (null != acb) overrideActions++;
        }
      }
      if (null != acb) {
        name2Actions.put(key, actionClass);
        name2Packages.put(key, pcb);
      }
    }
    Set<String> processedPackages = CollectUtils.newHashSet();
    // Add the new actions to the configuration
    for (PackageConfig.Builder builder : packageConfigs.values()) {
      String packageName = builder.getName();
      if (!processedPackages.contains(packageName)) {
        configuration.removePackageConfig(packageName);
        configuration.addPackageConfig(packageName, builder.build());
        processedPackages.add(packageName);
      }
    }
    templateFinder = null;
    logger.info("Action scan completed,create {} new action(override {}) in {}.", new Object[] { newActions,
        overrideActions, watch });
  }

  protected void initReloadClassLoader() {
    if (isReloadEnabled() && reloadingClassLoader == null) reloadingClassLoader = new ReloadingClassLoader(
        getClassLoader());
  }

  protected ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  protected ClassLoaderInterface getClassLoaderInterface() {
    if (isReloadEnabled()) return new ClassLoaderInterfaceDelegate(reloadingClassLoader);
    else {
      ClassLoaderInterface classLoaderInterface = null;
      ActionContext ctx = ActionContext.getContext();
      if (ctx != null) classLoaderInterface = (ClassLoaderInterface) ctx
          .get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);
      return Objects.defaultIfNull(classLoaderInterface, new ClassLoaderInterfaceDelegate(getClassLoader()));
    }
  }

  protected boolean isReloadEnabled() {
    return devMode;
  }

  protected ActionConfig.Builder createActionConfig(PackageConfig.Builder pkgCfg, Action action,
      Class<?> actionClass, String beanName) {
    ActionConfig.Builder acb = null;
    String actionName = action.getName();
    // check action exists on that package (from XML config probably)
    PackageConfig existedPkg = configuration.getPackageConfig(pkgCfg.getName());
    if (null == existedPkg || null == existedPkg.getActionConfigs().get(actionName)) {
      acb = new ActionConfig.Builder(pkgCfg.getName(), action.getName(), beanName);
      acb.methodName(action.getMethod());
      acb.addResultConfigs(buildResultConfigs(actionClass, pkgCfg));
      pkgCfg.addActionConfig(actionName, acb.build());
      logger.debug("Add {}/{} for {} in {}",
          new Object[] { pkgCfg.getNamespace(), actionName, actionClass.getName(), pkgCfg.getName() });
    }
    return acb;
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

  /**
   * generator default results by method name
   * 
   * @param clazz
   * @return
   */
  protected List<ResultConfig> buildResultConfigs(Class<?> clazz, PackageConfig.Builder pcb) {
    List<ResultConfig> configs = CollectUtils.newArrayList();
    // load annotation results
    Result[] results = new Result[0];
    Results rs = clazz.getAnnotation(Results.class);
    if (null == rs) {
      org.beangle.struts2.annotation.Action an = clazz
          .getAnnotation(org.beangle.struts2.annotation.Action.class);
      if (null != an) results = an.results();
    } else {
      results = rs.value();
    }
    Set<String> annotationResults = CollectUtils.newHashSet();
    if (null != results) {
      for (Result result : results) {
        String resultType = result.type();
        if (Strings.isEmpty(resultType)) resultType = "dispatcher";
        ResultTypeConfig rtc = pcb.getResultType(resultType);
        configs.add(new ResultConfig.Builder(result.name(), rtc.getClassName()).addParam(
            rtc.getDefaultResultParam(), result.location()).build());
        annotationResults.add(result.name());
      }
    }
    // load ftl convension results
    if (null == profileService) return configs;
    String extention = profileService.getProfile(clazz.getName()).getViewExtension();
    if (!extention.equals("ftl")) return configs;
    for (Method m : clazz.getMethods()) {
      String methodName = m.getName();
      if (!annotationResults.contains(methodName) && shouldGenerateResult(m)) {
        String path = templateFinder.find(clazz, methodName, methodName, extention);
        if (null != path) {
          ResultTypeConfig rtc = pcb.getResultType("freemarker");
          configs.add(new ResultConfig.Builder(m.getName(), rtc.getClassName()).addParam(
              rtc.getDefaultResultParam(), path).build());
        }
      }
    }
    return configs;
  }

  protected PackageConfig.Builder buildPackageConfig(final Class<?> actionClass, Action action,
      final Map<String, PackageConfig.Builder> packageConfigs) {
    String actionPackage = actionClass.getPackage().getName();
    PackageConfig.Builder pcb = null;
    PackageConfig parent = null;
    // find my package config builder and parent
    while (null == pcb && null == parent && Strings.contains(actionPackage, '.')) {
      pcb = packageConfigs.get(actionPackage);
      if (null != pcb && !pcb.getNamespace().equals(action.getNamespace())) {
        pcb = null;
      }
      parent = configuration.getPackageConfig(actionPackage);
      actionPackage = Strings.substringBeforeLast(actionPackage, ".");
    }
    // try default
    if (null == pcb) pcb = packageConfigs.get(defaultParentPackage);
    if (null == parent) parent = configuration.getPackageConfig(defaultParentPackage);
    if (!pcb.getNamespace().equals(action.getNamespace())) pcb = null;

    if (null != pcb) {
      packageConfigs.put(actionClass.getPackage().getName(), pcb);
    } else {
      // own package
      String newPkg = actionClass.getPackage().getName();
      if (parent.getName().equals(newPkg)) {
        if (parent.getNamespace().equals(action.getNamespace())) pcb = new PackageConfig.Builder(parent);
        else newPkg = actionClass.getName();
      } else {
        PackageConfig.Builder newly = packageConfigs.get(newPkg);
        if (null != newly && !newly.getNamespace().equals(action.getNamespace())) newPkg = actionClass
            .getName();
      }
      if (null == pcb) {
        pcb = new PackageConfig.Builder(newPkg).namespace(action.getNamespace()).addParent(parent);
        logger.debug("Created package config {} under {}", actionPackage, action.getNamespace());
      }
      packageConfigs.put(newPkg, pcb);
    }
    return pcb;
  }

  public boolean needsReload() {
    return devMode;
  }

  private TemplateFinder buildTemplateFinder() {
    ServletContext sc = ServletActionContext.getServletContext();
    TemplateLoader loader = freemarkerManager.getConfiguration(sc).getTemplateLoader();
    return new TemplateFinder(loader, viewMapper);
  }

}
