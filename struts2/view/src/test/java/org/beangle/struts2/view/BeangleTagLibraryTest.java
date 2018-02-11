/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ognl.PropertyAccessor;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.util.StrutsTestCaseHelper;
import org.apache.struts2.views.TagLibraryModelProvider;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.FreemarkerThemeTemplateLoader;
import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;
import org.apache.struts2.views.freemarker.tags.StrutsModels;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.struts2.freemarker.BeangleFreemarkerManager;
import org.beangle.struts2.freemarker.BeangleObjectWrapper;
import org.beangle.struts2.view.bean.ActionUriRender;
import org.beangle.struts2.view.bean.DefaultActionUriRender;
import org.beangle.struts2.view.tag.BeangleTagLibrary;
import org.beangle.struts2.view.template.FreemarkerTemplateEngine;
import org.beangle.struts2.view.template.TemplateEngine;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.FileManager;
import com.opensymphony.xwork2.FileManagerFactory;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.XWorkConstants;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.conversion.ConversionAnnotationProcessor;
import com.opensymphony.xwork2.conversion.ConversionFileProcessor;
import com.opensymphony.xwork2.conversion.ConversionPropertiesProcessor;
import com.opensymphony.xwork2.conversion.TypeConverterCreator;
import com.opensymphony.xwork2.conversion.TypeConverterHolder;
import com.opensymphony.xwork2.conversion.impl.DefaultConversionAnnotationProcessor;
import com.opensymphony.xwork2.conversion.impl.DefaultConversionFileProcessor;
import com.opensymphony.xwork2.conversion.impl.DefaultConversionPropertiesProcessor;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverterCreator;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverterHolder;
import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.factory.ActionFactory;
import com.opensymphony.xwork2.factory.ConverterFactory;
import com.opensymphony.xwork2.factory.DefaultActionFactory;
import com.opensymphony.xwork2.factory.DefaultConverterFactory;
import com.opensymphony.xwork2.factory.DefaultInterceptorFactory;
import com.opensymphony.xwork2.factory.DefaultResultFactory;
import com.opensymphony.xwork2.factory.DefaultUnknownHandlerFactory;
import com.opensymphony.xwork2.factory.InterceptorFactory;
import com.opensymphony.xwork2.factory.ResultFactory;
import com.opensymphony.xwork2.factory.UnknownHandlerFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Scope;
import com.opensymphony.xwork2.ognl.OgnlReflectionProvider;
import com.opensymphony.xwork2.ognl.OgnlUtil;
import com.opensymphony.xwork2.ognl.OgnlValueStackFactory;
import com.opensymphony.xwork2.ognl.accessor.CompoundRootAccessor;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.XWorkTestCaseHelper;
import com.opensymphony.xwork2.util.fs.DefaultFileManager;
import com.opensymphony.xwork2.util.fs.DefaultFileManagerFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionProvider;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Test
public class BeangleTagLibraryTest {

  protected ConfigurationManager configurationManager;
  protected Configuration configuration;
  protected Container container;
  protected ActionProxyFactory actionProxyFactory;

  @BeforeClass
  public void setUp() throws Exception {
    configurationManager = XWorkTestCaseHelper.setUp();
    configuration = configurationManager.getConfiguration();
    container = configuration.getContainer();
    actionProxyFactory = container.getInstance(ActionProxyFactory.class);
    buildCfg();
    // initDispatcher(null);
  }

  protected Dispatcher initDispatcher(Map<String, String> params) {
    Dispatcher du = StrutsTestCaseHelper.initDispatcher(mock(ServletContext.class), params);
    configurationManager = du.getConfigurationManager();
    configuration = configurationManager.getConfiguration();
    container = configuration.getContainer();
    return du;
  }

  @AfterClass
  public void tearDown() throws Exception {
    XWorkTestCaseHelper.tearDown(configurationManager);
    configurationManager = null;
    configuration = null;
    container = null;
    actionProxyFactory = null;
    StrutsTestCaseHelper.tearDown();
  }

  freemarker.template.Configuration cfg;

  protected void buildCfg() {
    cfg = new freemarker.template.Configuration();
    cfg.setTemplateLoader(new StrutsClassTemplateLoader());
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
    cfg.setObjectWrapper(new BeangleObjectWrapper(true));
    cfg.setDefaultEncoding("utf-8");
  }

  @SuppressWarnings("unused")
  public void testText() throws Exception {
    Map<String, Object> datas = CollectUtils.newHashMap();
    datas.put("b", new BeangleTagLibrary());
    datas.put("s", new StrutsModels(ActionContext.getContext().getValueStack(),
        mock(HttpServletRequest.class), mock(HttpServletResponse.class)));
    datas.put("watch", new Stopwatch());
    StringWriter writer = new StringWriter();
    Template template = cfg.getTemplate("comp.ftl");
    // template.process(datas, writer);
  }

  public void testForm() throws Exception {
    Map<String, Object> datas = CollectUtils.newHashMap();
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ServletContext servletContext = mock(ServletContext.class);
    when(request.getRequestURI()).thenReturn("/");

    Container container = buildContainer();
    ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
    stack.getContext().put(StrutsStatics.HTTP_REQUEST, request);
    stack.getContext().put(ServletActionContext.SERVLET_CONTEXT, servletContext);

    when(servletContext.getInitParameter("TemplatePath")).thenReturn("class://");

    // build action action context
    ActionContext context = new ActionContext(stack.getContext());
    ActionContext.setContext(context);
    context.setContainer(container);
    context.setSession(new HashMap<String, Object>());
    FreemarkerManager freemarker = container.getInstance(FreemarkerManager.class);
    freemarker.getConfiguration(servletContext);
    datas.put("b", new BeangleTagLibrary().getModels(stack, request, response));
    datas.put("s", new StrutsModels(stack, request, response));
    datas.put("watch", new Stopwatch());
    StringWriter writer = new StringWriter();
    Template template = cfg.getTemplate("form.ftl");
    template.process(datas, writer);
    assert null != writer.toString();
  }

  /**
   * borrow from com.opensymphony.xwork2.config.impl.DefaultConfiguration.createBootstrapContainer
   *
   * @return
   */
  private Container buildContainer() {
    // build container
    ContainerBuilder builder = new ContainerBuilder();
    builder.factory(ObjectFactory.class, Scope.SINGLETON);
    builder.factory(ValueStackFactory.class, OgnlValueStackFactory.class, Scope.SINGLETON);
    builder.factory(XWorkConverter.class, Scope.SINGLETON);
    builder.factory(PropertyAccessor.class, CompoundRoot.class.getName(), CompoundRootAccessor.class,
        Scope.SINGLETON);
    builder.factory(ReflectionProvider.class, OgnlReflectionProvider.class, Scope.SINGLETON);
    builder.factory(OgnlUtil.class, Scope.SINGLETON);
    builder.factory(FileManager.class, "system", DefaultFileManager.class, Scope.SINGLETON);
    builder.factory(FileManagerFactory.class, DefaultFileManagerFactory.class, Scope.SINGLETON);
    builder.factory(XWorkBasicConverter.class, Scope.SINGLETON);
    builder.factory(ConversionPropertiesProcessor.class, DefaultConversionPropertiesProcessor.class,
        Scope.SINGLETON);
    builder.factory(ConversionFileProcessor.class, DefaultConversionFileProcessor.class, Scope.SINGLETON);
    builder.factory(ConversionAnnotationProcessor.class, DefaultConversionAnnotationProcessor.class,
        Scope.SINGLETON);
    builder.factory(TypeConverterCreator.class, DefaultTypeConverterCreator.class, Scope.SINGLETON);
    builder.factory(TypeConverterHolder.class, DefaultTypeConverterHolder.class, Scope.SINGLETON);
    builder.factory(UnknownHandlerFactory.class, DefaultUnknownHandlerFactory.class, Scope.SINGLETON);
    builder.factory(TextProvider.class, "system", DefaultTextProvider.class, Scope.SINGLETON);
    builder.factory(ActionUriRender.class, DefaultActionUriRender.class, Scope.SINGLETON);
    builder.factory(TagLibraryModelProvider.class, "b", BeangleTagLibrary.class, Scope.SINGLETON);
    builder.factory(ActionFactory.class, DefaultActionFactory.class, Scope.SINGLETON);
    builder.factory(ResultFactory.class, DefaultResultFactory.class, Scope.SINGLETON);
    builder.factory(InterceptorFactory.class, DefaultInterceptorFactory.class, Scope.SINGLETON);
    builder.factory(com.opensymphony.xwork2.factory.ValidatorFactory.class,
        com.opensymphony.xwork2.factory.DefaultValidatorFactory.class, Scope.SINGLETON);
    builder.factory(ConverterFactory.class, DefaultConverterFactory.class, Scope.SINGLETON);
    builder.factory(FreemarkerManager.class, BeangleFreemarkerManager.class, Scope.SINGLETON);
    builder.factory(FreemarkerThemeTemplateLoader.class, Scope.SINGLETON);
    builder.factory(org.apache.struts2.components.template.TemplateEngine.class, "ftl",
        org.apache.struts2.components.template.FreemarkerTemplateEngine.class, Scope.SINGLETON);
    builder.factory(TemplateEngine.class, FreemarkerTemplateEngine.class, Scope.SINGLETON);
    builder.constant(XWorkConstants.DEV_MODE, "false");
    builder.constant(XWorkConstants.LOG_MISSING_PROPERTIES, "false");
    builder.constant(XWorkConstants.ENABLE_OGNL_EVAL_EXPRESSION, "false");
    builder.constant(XWorkConstants.ENABLE_OGNL_EXPRESSION_CACHE, "true");
    builder.constant("struts.ui.theme.expansion.token", "~~~");
    builder.constant(XWorkConstants.RELOAD_XML_CONFIGURATION, "false");
    builder.constant(StrutsConstants.STRUTS_I18N_ENCODING, "utf-8");
    builder.constant(StrutsConstants.STRUTS_ACTION_EXTENSION, "action");
    builder.constant(StrutsConstants.STRUTS_FREEMARKER_WRAPPER_ALT_MAP, "false");
    builder.constant(StrutsConstants.STRUTS_FREEMARKER_BEANWRAPPER_CACHE, "true");
    builder.constant(StrutsConstants.STRUTS_FREEMARKER_MRU_MAX_STRONG_SIZE, "10");
    return builder.create(true);
  }
}
