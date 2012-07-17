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
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.web.spring.ContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.reflection.ReflectionException;
import com.opensymphony.xwork2.util.reflection.ReflectionExceptionHandler;

/**
 * <pre>
 * 1 Specialize for freemarker result creation.
 * 2 Cached struts bean with innerFactory map
 * 3 Dropped spring autowire feature for without any bean needed(action?struts2?)
 * </pre>
 * 
 * @author chaostone
 * @version $Id: BeangleSpringObjectFactory.java Dec 25, 2011 5:54:57 PM chaostone $
 */
public class BeangleObjectFactory extends ObjectFactory {
  private static final long serialVersionUID = -1733081389212973935L;

  private static final Logger logger = LoggerFactory.getLogger(BeangleObjectFactory.class);

  protected ApplicationContext appContext;

  @Inject
  private FreemarkerManager freemarkerManager;

  /**
   * Extra bean not managed by spring
   */
  private Map<String, Object> innerFactory = CollectUtils.newHashMap();

  /**
   * Constructs the spring object factory
   * 
   * @param servletContext The servlet context
   * @since 2.1.3
   */
  @Inject
  public BeangleObjectFactory(
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE, required = false) String autoWire,
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE_ALWAYS_RESPECT, required = false) String alwaysAutoWire,
      @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE, required = false) String useClassCacheStr,
      @Inject ServletContext servletContext, @Inject(StrutsConstants.STRUTS_DEVMODE) String devMode,
      @Inject Container container) {

    logger.info("Initializing Struts-Spring integration...");
    appContext = ContextLoader.getContext(servletContext);
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
    logger.info("Initialized Struts-Spring integration successfully");
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
    Object bean = null;
    if (appContext.containsBeanDefinition(beanName)) {
      bean = appContext.getBean(beanName);
      if (injectInternal) injectInternalBeans(bean);
    } else {
      bean = innerFactory.get(beanName);
      if (null == bean) {
        bean = buildBean(getClassInstance(beanName), extraContext);
        if (null != bean) innerFactory.put(beanName, bean);
      }
    }
    return bean;
  }

  /**
   * @param clazz
   * @param extraContext
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
    Object bean = clazz.newInstance();
    if (bean instanceof ApplicationContextAware) ((ApplicationContextAware) bean)
        .setApplicationContext(appContext);
    bean = injectInternalBeans(bean);
    return bean;
  }

  public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
    this.freemarkerManager = freemarkerManager;
  }

  @Override
  public Class<?> getClassInstance(String className) throws ClassNotFoundException {
    Class<?> clazz = null;
    if (appContext.containsBean(className)) {
      clazz = appContext.getBean(className).getClass();
    } else {
      clazz = super.getClassInstance(className);
    }
    return clazz;
  }

  public ApplicationContext getApplicationContext() {
    return appContext;
  }

}
