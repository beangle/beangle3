/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;

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

  public final static class ReferenceValue {
    public final String ref;

    public ReferenceValue(String ref) {
      super();
      this.ref = ref;
    }
  }

  public final static class ListValue {
    public final List<Object> items = CollectUtils.newArrayList();

    public ListValue(Object... datas) {
      for (Object obj : datas) {
        if (obj instanceof Class<?>) {
          items.add(new ReferenceValue(((Class<?>) obj).getName()));
        } else {
          items.add(obj);
        }
      }
    }
  }

  public final static class MapValue {
    public final Map<Object, Object> items = CollectUtils.newHashMap();

    public MapValue(Pair<?, ?>... datas) {
      for (Map.Entry<?, ?> entry : datas) {
        if (entry.getValue() instanceof Class<?>) {
          items.put(entry.getKey(), new ReferenceValue(((Class<?>) entry.getValue()).getName()));
        } else {
          items.put(entry.getKey(), entry.getValue());
        }
      }
    }
  }

  public final static class PropertiesValue {
    public final Map<String, String> properties = CollectUtils.newHashMap();

    public PropertiesValue(String... keyValuePairs) {
      for (String pair : keyValuePairs) {
        Assert.isTrue(pair.indexOf('=') > 0, "property entry [" + pair + "] should contain =");
        properties.put(Strings.substringBefore(pair, "="), Strings.substringAfter(pair, "="));
      }
    }
  }

  /**
   * Bean Definition
   * @author chaostone
   * @since 3.0.0
   */
  public final static class Definition {
    public String beanName;
    public final Class<?> clazz;
    public String scope;
    public String initMethod;
    public Map<String, Object> properties = CollectUtils.newHashMap();

    public boolean lazyInit = false;
    public boolean abstractFlag = false;

    public boolean primary = false;
    public String parent;

    public Class<?> targetClass;

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
    private List<Definition> last = CollectUtils.newArrayList(0);

    public DefinitionBinder(BeanConfig config, Class<?>... classes) {
      super();
      this.config = config;
      bind(classes);
    }

    public DefinitionBinder shortName() {
      return shortName(true);
    }

    public DefinitionBinder lazy() {
      for (Definition def : last)
        def.lazyInit = true;
      return this;
    }

    public DefinitionBinder parent(String parent) {
      for (Definition def : last)
        def.parent = parent;
      return this;
    }

    public DefinitionBinder proxy(String property, Class<?> clazz) {
      // first bind inner bean
      StringBuilder sb = new StringBuilder();
      for (Definition def : last)
        sb.append(def.beanName);
      String targetBean = clazz.getName() + "#" + Math.abs(sb.hashCode());
      config.add(new Definition(targetBean, clazz, Scope.SINGLETON.toString()));
      // second
      for (Definition def : last) {
        def.targetClass = clazz;
        def.properties.put(property, new ReferenceValue(targetBean));
      }
      return this;
    }

    public DefinitionBinder primary() {
      for (Definition def : last)
        def.primary = true;
      return this;
    }

    public DefinitionBinder setAbstract() {
      for (Definition def : last)
        def.abstractFlag = true;
      return this;
    }

    public DefinitionBinder shortName(boolean b) {
      for (Definition def : last)
        def.beanName = getBeanName(def.clazz, b);
      return this;
    }

    public DefinitionBinder in(Scope scope) {
      for (Definition def : last)
        def.scope = scope.toString();
      return this;
    }

    public DefinitionBinder property(String property, Object value) {
      for (Definition def : last) {
        if (value instanceof Class<?>) def.properties.put(property,
            new ReferenceValue(((Class<?>) value).getName()));
        else def.properties.put(property, value);
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
      for (Definition def : last)
        def.initMethod = method;
      return this;
    }

    private DefinitionBinder bind(Class<?>... classes) {
      for (Class<?> clazz : classes) {
        Definition def = build(clazz);
        config.add(def);
        last.add(def);
      }
      return this;
    }

    private DefinitionBinder bind(String name, Class<?> clazz) {
      Definition def = new Definition(name, clazz, Scope.SINGLETON.toString());
      config.add(def);
      last.add(def);
      return this;
    }

    private String getBeanName(Class<?> clazz, boolean shortName) {
      String className = clazz.getName();
      if (shortName) className = Strings.uncapitalize(Strings.substringAfterLast(className, "."));
      return className;
    }

    private Definition build(Class<?> clazz) {
      return new Definition(getBeanName(clazz, false), clazz, Scope.SINGLETON.toString());
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
