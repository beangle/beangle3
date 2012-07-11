/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * BeanConfig class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class BeanConfig {

  private List<Definition> definitions = CollectUtils.newArrayList();

  private List<Definition> lastAdded;

  public final static class ReferenceProperty {
    public final String ref;

    public ReferenceProperty(String ref) {
      super();
      this.ref = ref;
    }

  }

  public final static class ListProperty {
    public final List<Object> items = CollectUtils.newArrayList();

    public ListProperty(Object... datas) {
      for (Object obj : datas) {
        if (obj instanceof Class<?>) {
          items.add(new ReferenceProperty(((Class<?>) obj).getName()));
        } else {
          items.add(obj);
        }
      }
    }
  }

  public final static class Definition {
    public String beanName;
    public final Class<?> clazz;
    public String scope;
    public String initMethod;
    public Map<String, Object> properties = CollectUtils.newHashMap();

    public boolean lazyInit = false;
    public boolean abstractFlag = false;

    public Definition(String beanName, Class<?> clazz, String scope) {
      super();
      this.beanName = beanName;
      this.clazz = clazz;
      if (null == scope) {
        this.scope = "singleton";
      } else {
        this.scope = scope;
      }
    }

    public boolean isAbstract() {
      return abstractFlag;
    }

    public Map<String, Object> getProperties() {
      return properties;
    }

    public Definition property(String property, Object value) {
      properties.put(property, value);
      return this;
    }

  }

  public final static class DefinitionBinder {
    private BeanConfig config;
    private Scope scope = Scope.SINGLETON;
    private boolean useShortName;

    public DefinitionBinder(BeanConfig config, Class<?>... classes) {
      super();
      this.config = config;
      bind(classes);
    }

    public DefinitionBinder(BeanConfig config, boolean useShortName) {
      super();
      this.config = config;
      this.useShortName = useShortName;
    }

    public DefinitionBinder(BeanConfig config, Scope scope) {
      super();
      this.config = config;
      this.scope = scope;
    }

    public DefinitionBinder shortName() {
      return shortName(true);
    }

    public DefinitionBinder lazy() {
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.lazyInit = true;
        }
      }
      return this;
    }

    public DefinitionBinder setAbstract() {
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.abstractFlag = true;
        }
      }
      return this;
    }

    public DefinitionBinder shortName(boolean b) {
      useShortName = b;
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.beanName = getBeanName(def.clazz);
        }
      }
      return this;
    }

    public DefinitionBinder in(Scope scope) {
      this.scope = scope;
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.scope = scope.toString();
        }
      }
      return this;
    }

    public DefinitionBinder property(String property, Object value) {
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.properties.put(property, value);
        }
      }
      return this;
    }

    public DefinitionBinder list(String property, Object... datas) {
      if (null != config.lastAdded) {
        Object value = new ListProperty(datas);
        for (Definition def : config.lastAdded) {
          def.properties.put(property, value);
        }
      }
      return this;
    }

    /**
     * assign init method
     * 
     * @param method
     * @return
     */
    public DefinitionBinder init(String method) {
      if (null != config.lastAdded) {
        for (Definition def : config.lastAdded) {
          def.initMethod = method;
        }
      }
      return this;
    }

    public DefinitionBinder bind(Class<?>... classes) {
      config.lastAdded = CollectUtils.newArrayList();
      for (Class<?> clazz : classes) {
        Definition def = build(clazz);
        config.add(def);
        config.lastAdded.add(def);
      }
      return this;
    }

    public DefinitionBinder bind(String name, Class<?> clazz) {
      config.lastAdded = CollectUtils.newArrayList();
      Definition def = new Definition(name, clazz, scope.toString());
      config.add(def);
      config.lastAdded.add(def);
      return this;
    }

    private String getBeanName(Class<?> clazz) {
      String className = clazz.getName();
      if (useShortName) className = Strings.uncapitalize(Strings.substringAfterLast(className, "."));
      return className;
    }

    private Definition build(Class<?> clazz) {
      return new Definition(getBeanName(clazz), clazz, scope.toString());
    }
  }

  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  public DefinitionBinder bind(String beanName, Class<?> clazz) {
    DefinitionBinder binder = new DefinitionBinder(this);
    binder.bind(beanName, clazz);
    return binder;
  }

  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param classes a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  public DefinitionBinder bind(Class<?>... classes) {
    return new DefinitionBinder(this, classes);
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param def a {@link org.beangle.commons.context.inject.BeanConfig.Definition} object.
   */
  protected void add(Definition def) {
    definitions.add(def);
  }

  /**
   * <p>
   * Getter for the field <code>definitions</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Definition> getDefinitions() {
    return definitions;
  }

}
