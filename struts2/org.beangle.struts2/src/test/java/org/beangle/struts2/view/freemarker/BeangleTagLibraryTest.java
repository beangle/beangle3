/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.io.StringWriter;
import java.util.Map;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.util.StrutsTestCaseHelper;
import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.view.components.BeangleModels;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Context;
import com.opensymphony.xwork2.inject.Factory;
import com.opensymphony.xwork2.inject.Scope;
import com.opensymphony.xwork2.test.StubConfigurationProvider;
import com.opensymphony.xwork2.util.XWorkTestCaseHelper;
import com.opensymphony.xwork2.util.location.LocatableProperties;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

//@Test
public class BeangleTagLibraryTest {

	protected ConfigurationManager configurationManager;
	protected Configuration configuration;
	protected Container container;
	protected ActionProxyFactory actionProxyFactory;

	// @BeforeClass
	public void setUp() throws Exception {
		configurationManager = XWorkTestCaseHelper.setUp();
		configuration = configurationManager.getConfiguration();
		container = configuration.getContainer();
		actionProxyFactory = container.getInstance(ActionProxyFactory.class);
		buildCfg();
		initDispatcher(null);
	}

	protected Dispatcher initDispatcher(Map<String, String> params) {
		Dispatcher du = StrutsTestCaseHelper.initDispatcher(new MockServletContext(), params);
		configurationManager = du.getConfigurationManager();
		configuration = configurationManager.getConfiguration();
		container = configuration.getContainer();
		return du;
	}

	// @AfterClass
	public void tearDown() throws Exception {
		XWorkTestCaseHelper.tearDown(configurationManager);
		configurationManager = null;
		configuration = null;
		container = null;
		actionProxyFactory = null;
		StrutsTestCaseHelper.tearDown();
	}

	protected void loadConfigurationProviders(ConfigurationProvider... providers) {
		configurationManager = XWorkTestCaseHelper.loadConfigurationProviders(configurationManager,
				providers);
		configuration = configurationManager.getConfiguration();
		container = configuration.getContainer();
		actionProxyFactory = container.getInstance(ActionProxyFactory.class);
	}

	protected void loadButAdd(final Class<?> type, final Object impl) {
		loadButAdd(type, Container.DEFAULT_NAME, impl);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void loadButAdd(final Class<?> type, final String name, final Object impl) {
		loadConfigurationProviders(new StubConfigurationProvider() {
			@Override
			public void register(ContainerBuilder builder, LocatableProperties props)
					throws ConfigurationException {
				builder.factory(type, name, new Factory() {
					public Object create(Context context) throws Exception {
						return impl;
					}

				}, Scope.SINGLETON);
			}
		});
	}

	freemarker.template.Configuration cfg;

	protected void buildCfg() {
		cfg = new freemarker.template.Configuration();
		cfg.setTemplateLoader(new StrutsClassTemplateLoader());
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setObjectWrapper(new BeangleObjectWrapper(true));
		cfg.setDefaultEncoding("utf-8");
	}

	public void testText() throws Exception {
		Template template = cfg.getTemplate("beangle-tags.ftl");
		StringWriter writer = new StringWriter();
		Map<String, Object> datas = CollectUtils.newHashMap();
		datas.put("bg", new BeangleModels(ActionContext.getContext().getValueStack(),
				new MockHttpServletRequest(), new MockHttpServletResponse()));
		template.process(datas, writer);
	}
}
