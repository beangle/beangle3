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
package org.beangle.struts2.view;

import static org.mockito.Mockito.mock;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.util.StrutsTestCaseHelper;
import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;
import org.apache.struts2.views.freemarker.tags.StrutsModels;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.struts2.freemarker.BeangleObjectWrapper;
import org.beangle.struts2.view.tag.BeangleTagLibrary;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.XWorkTestCaseHelper;

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
}
