/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.spring;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.FreemarkerResult;
import org.beangle.commons.web.spring.ContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.spring.SpringObjectFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionException;
import com.opensymphony.xwork2.util.reflection.ReflectionExceptionHandler;

/**
 * <pre>
 * 1 specialize for freemarker result creation.
 * 2 prevent buildbean injectinternal twice
 * </pre>
 * 
 * @author chaostone
 * @version $Id: BeangleSpringObjectFactory.java Dec 25, 2011 5:54:57 PM chaostone $
 */
public class BeangleSpringObjectFactory extends SpringObjectFactory {
  private static final long serialVersionUID = -1733081389212973935L;

  private static final Logger logger = LoggerFactory.getLogger(BeangleSpringObjectFactory.class);

  @Inject
  private FreemarkerManager freemarkerManager;

  /**
   * Constructs the spring object factory
   * 
   * @param autoWire
   *          The type of autowiring to use
   * @param alwaysAutoWire
   *          Whether to always respect the autowiring or not
   * @param useClassCacheStr
   *          Whether to use the class cache or not
   * @param servletContext
   *          The servlet context
   * @since 2.1.3
   */
  @Inject
  public BeangleSpringObjectFactory(
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE, required = false) String autoWire,
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE_ALWAYS_RESPECT, required = false) String alwaysAutoWire,
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE, required = false) String useClassCacheStr,
      @Inject ServletContext servletContext, @Inject(StrutsConstants.STRUTS_DEVMODE) String devMode,
      @Inject Container container) {

    super();
    boolean useClassCache = "true".equals(useClassCacheStr);
    logger.info("Initializing Struts-Spring integration...");

    ApplicationContext appContext = ContextLoader.getContext(servletContext);
    if (appContext == null) {
      // uh oh! looks like the lifecycle listener wasn't installed. Let's inform the user
      String message = "********** FATAL ERROR STARTING UP STRUTS-SPRING INTEGRATION **********\n"
          + "Looks like the Spring listener was not configured for your web app! \n"
          + "Nothing will work until WebApplicationContextUtils returns a valid ApplicationContext.\n"
          + "You might need to add the following to web.xml: \n" + "    <listener>\n"
          + "        <listener-class>org.beangle.web.spring.ContextListener</listener-class>\n"
          + "    </listener>";
      logger.error(message);
      return;
    }

    // String watchList = container.getInstance(String.class,
    // "struts.class.reloading.watchList");
    // String acceptClasses = container.getInstance(String.class,
    // "struts.class.reloading.acceptClasses");
    // String reloadConfig = container.getInstance(String.class,
    // "struts.class.reloading.reloadConfig");
    //
    // if ("true".equals(devMode) && Strings.isNotBlank(watchList)
    // && appContext instanceof ClassReloadingXMLWebApplicationContext) {
    // // prevent class caching
    // useClassCache = false;
    //
    // ClassReloadingXMLWebApplicationContext reloadingContext =
    // (ClassReloadingXMLWebApplicationContext) appContext;
    // reloadingContext.setupReloading(watchList.split(","), acceptClasses, servletContext,
    // "true".equals(reloadConfig));
    // logger.info("Class reloading is enabled. Make sure this is not used on a production environment!",
    // watchList);
    //
    // setClassLoader(reloadingContext.getReloadingClassLoader());
    //
    // // we need to reload the context, so our isntance of the factory is picked up
    // reloadingContext.refresh();
    // }

    this.setApplicationContext(appContext);

    int type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME; // default
    if ("name".equals(autoWire)) {
      type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
    } else if ("type".equals(autoWire)) {
      type = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
    } else if ("constructor".equals(autoWire)) {
      type = AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
    } else if ("no".equals(autoWire)) {
      type = AutowireCapableBeanFactory.AUTOWIRE_NO;
    }
    this.setAutowireStrategy(type);

    this.setUseClassCache(useClassCache);

    this.setAlwaysRespectAutowireStrategy("true".equalsIgnoreCase(alwaysAutoWire));

    logger.info("... initialized Struts-Spring integration successfully");
  }

  /**
   * 为freemaker做优化
   */
  public Result buildResult(ResultConfig resultConfig, Map<String, Object> extraContext) throws Exception {
    String resultClassName = resultConfig.getClassName();
    Result result = null;
    if (resultClassName != null) {
      if (resultClassName.equals("org.apache.struts2.views.freemarker.FreemarkerResult")) {
        result = new FreemarkerResult();
        FreemarkerResult fresult = (FreemarkerResult) result;
        fresult.setFreemarkerManager(freemarkerManager);
        fresult.setLocation(resultConfig.getParams().get("location"));
      } else {
        result = (Result) buildBean(resultClassName, extraContext);
        Map<String, String> params = resultConfig.getParams();
        if (params != null) {
          for (Map.Entry<String, String> paramEntry : params.entrySet()) {
            try {
              reflectionProvider.setProperty(paramEntry.getKey(), paramEntry.getValue(), result,
                  extraContext, true);
            } catch (ReflectionException ex) {
              if (result instanceof ReflectionExceptionHandler) {
                ((ReflectionExceptionHandler) result).handle(ex);
              }
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * Looks up beans using Spring's application context before falling back to the method defined
   * in the {@link ObjectFactory}.
   * 
   * @param beanName
   *          The name of the bean to look up in the application context
   * @param extraContext
   * @return A bean from Spring or the result of calling the overridden
   *         method.
   * @throws Exception
   */
  @Override
  public Object buildBean(String beanName, Map<String, Object> extraContext, boolean injectInternal)
      throws Exception {
    Object o = null;
    if (appContext.containsBeanDefinition(beanName)) {
      o = appContext.getBean(beanName);
      if (injectInternal) injectInternalBeans(o);
    } else {
      Class<?> beanClazz = getClassInstance(beanName);
      o = buildBean(beanClazz, extraContext);
    }
    return o;
  }

  public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
    this.freemarkerManager = freemarkerManager;
  }

}
