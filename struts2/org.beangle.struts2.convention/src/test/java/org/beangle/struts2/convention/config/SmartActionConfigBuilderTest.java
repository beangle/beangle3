/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.i18n.impl.DefaultTextBundleRegistry;
import org.beangle.struts2.convention.route.ActionBuilder;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.impl.DefaultActionBuilder;
import org.beangle.struts2.convention.route.impl.ProfileServiceImpl;
import org.testng.annotations.Test;

import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorConfig;
import com.opensymphony.xwork2.config.entities.InterceptorStackConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.config.impl.DefaultConfiguration;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Scope.Strategy;
import com.opensymphony.xwork2.ognl.OgnlReflectionProvider;
import com.opensymphony.xwork2.util.reflection.ReflectionException;

@Test
public class SmartActionConfigBuilderTest {

  public void test21() throws Exception {
    ActionBuilder actionNameBuilder = new DefaultActionBuilder();
    ProfileService profileService = new ProfileServiceImpl();
    actionNameBuilder.setProfileService(profileService);

    ObjectFactory of = new ObjectFactory();
    final DummyContainer mockContainer = new DummyContainer();
    Configuration configuration = new DefaultConfiguration() {
      private static final long serialVersionUID = 1L;

      @Override
      public Container getContainer() {
        return mockContainer;
      }
    };
    PackageConfig strutsDefault = makePackageConfig("beangle", null, null, "dispatcher", null, null, null);
    configuration.addPackageConfig("beangle", strutsDefault);
    // ResultMapBuilder resultMapBuilder =
    // createStrictMock(ResultMapBuilder.class);
    // set beans on mock container
    mockContainer.setActionNameBuilder(actionNameBuilder);
    // mockContainer.setResultMapBuilder(resultMapBuilder);
    // mockContainer.setConventionsService(new ConventionsServiceImpl(""));

    SmartActionConfigBuilder builder = new SmartActionConfigBuilder(configuration, mockContainer, of);
    builder.setActionBuilder(actionNameBuilder);
    builder.setRegistry(new DefaultTextBundleRegistry());
    
    builder.buildActionConfigs();
    Set<String> names = configuration.getPackageConfigNames();
    for (String a : names) {
      System.out.println("pkgname:" + a);
      PackageConfig pkgConfig = configuration.getPackageConfig(a);
      System.out.println("namespace:" + pkgConfig.getNamespace());
      Map<String, ActionConfig> configs = pkgConfig.getAllActionConfigs();
      for (String actionName : configs.keySet()) {
        ActionConfig config = configs.get(actionName);
        System.out.println(config.getClassName());
        System.out.println(config.getName());
        System.out.println(config.getMethodName());
      }
    }
  }

  // private void verifyActionConfig(PackageConfig pkgConfig, String
  // actionName,
  // Class<?> actionClass, String methodName, String packageName) {
  // ActionConfig ac = pkgConfig.getAllActionConfigs().get(actionName);
  // assertNotNull(ac);
  // assertEquals(actionClass.getName(), ac.getClassName());
  // assertEquals(methodName, ac.getMethodName());
  // assertEquals(packageName, ac.getPackageName());
  // }

  public class MyPackageConfig extends PackageConfig {
    private static final long serialVersionUID = 1L;

    protected MyPackageConfig(PackageConfig packageConfig) {
      super(packageConfig);
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof PackageConfig)) return false;
      PackageConfig other = (PackageConfig) obj;
      return getName().equals(other.getName()) && getNamespace().equals(other.getNamespace())
          && getParents().get(0) == other.getParents().get(0)
          && getParents().size() == other.getParents().size();
    }
  }

  private PackageConfig makePackageConfig(String name, String namespace, PackageConfig parent,
      String defaultResultType, ResultTypeConfig[] results, List<InterceptorConfig> interceptors,
      List<InterceptorStackConfig> interceptorStacks) {
    PackageConfig.Builder builder = new PackageConfig.Builder(name);
    if (namespace != null) {
      builder.namespace(namespace);
    }
    if (parent != null) {
      builder.addParent(parent);
    }
    if (defaultResultType != null) {
      builder.defaultResultType(defaultResultType);
    }
    if (results != null) {
      for (ResultTypeConfig result : results) {
        builder.addResultTypeConfig(result);
      }
    }
    if (interceptors != null) {
      for (InterceptorConfig ref : interceptors) {
        builder.addInterceptorConfig(ref);
      }
    }
    if (interceptorStacks != null) {
      for (InterceptorStackConfig ref : interceptorStacks) {
        builder.addInterceptorStackConfig(ref);
      }
    }

    return new MyPackageConfig(builder.build());
  }

  public class DummyContainer implements Container {
    private static final long serialVersionUID = 1L;
    private ActionBuilder actionNameBuilder;

    public void setActionNameBuilder(ActionBuilder actionNameBuilder) {
      this.actionNameBuilder = actionNameBuilder;
    }

    public <T> T getInstance(Class<T> type) {
      try {
        T obj = type.newInstance();
        if (obj instanceof ObjectFactory) {
          ((ObjectFactory) obj).setReflectionProvider(new OgnlReflectionProvider() {

            @Override
            public void setProperties(Map<String, String> properties, Object o) {
            }

            public void setProperties(Map<String, String> properties, Object o, Map<String, Object> context,
                boolean throwPropertyExceptions) throws ReflectionException {
              if (o instanceof ActionChainResult) {
                ((ActionChainResult) o).setActionName(properties.get("actionName"));
              }
            }

            @Override
            public void setProperty(String name, Object value, Object o, Map<String, Object> context,
                boolean throwPropertyExceptions) {
              if (o instanceof ActionChainResult) {
                ((ActionChainResult) o).setActionName((String) value);
              }
            }
          });
        }
        return obj;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type, String name) {
      if (type == ActionBuilder.class) return (T) actionNameBuilder;
      return null;
    }

    public Set<String> getInstanceNames(Class<?> type) {
      return null;
    }

    public void inject(Object o) {

    }

    public <T> T inject(Class<T> implementation) {
      return null;
    }

    public void removeScopeStrategy() {

    }

    public void setScopeStrategy(Strategy scopeStrategy) {
    }

  }
}
