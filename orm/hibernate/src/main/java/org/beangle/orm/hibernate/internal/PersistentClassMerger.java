package org.beangle.orm.hibernate.internal;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.MappedSuperclass;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将子类的persistent class 合并到父类中。
 * 
 * @author chaostone
 * @since 3.4.9
 */
class PersistentClassMerger {

  private static Logger logger = LoggerFactory.getLogger(OverrideConfiguration.class);

  private static Field subPropertyField = null;
  private static Field declarePropertyField = null;
  private static Field subclassField = null;
  static {
    try {
      subPropertyField = PersistentClass.class.getDeclaredField("subclassProperties");
      declarePropertyField = PersistentClass.class.getDeclaredField("declaredProperties");
      subclassField = PersistentClass.class.getDeclaredField("subclasses");
      declarePropertyField.setAccessible(true);
      subPropertyField.setAccessible(true);
      subclassField.setAccessible(true);
    } catch (Exception e) {
      logger.error("Cannot get PersistentClass private field,Override Mapping will be disabled", e);
      subPropertyField = null;
      declarePropertyField = null;
      subclassField = null;
    }
  }

  public static void mergeInto(PersistentClass sub, PersistentClass parent) {
    String className = sub.getClassName();
    // 1. convert old to mappedsuperclass
    MappedSuperclass msc = new MappedSuperclass(parent.getSuperMappedSuperclass(), null);
    msc.setMappedClass(parent.getMappedClass());

    // 2.clear old subclass property
    parent.setSuperMappedSuperclass(msc);
    parent.setClassName(className);
    parent.setProxyInterfaceName(className);

    try {
      Field subPropertyField = PersistentClass.class.getDeclaredField("subclassProperties");
      Field declarePropertyField = PersistentClass.class.getDeclaredField("declaredProperties");
      Field subclassField = PersistentClass.class.getDeclaredField("subclasses");
      declarePropertyField.setAccessible(true);
      subPropertyField.setAccessible(true);
      subclassField.setAccessible(true);
      @SuppressWarnings("unchecked")
      List<Property> declareProperties = (List<Property>) declarePropertyField.get(parent);
      for (Property p : declareProperties)
        msc.addDeclaredProperty(p);
      ((List<?>) subPropertyField.get(parent)).clear();
      ((List<?>) subclassField.get(parent)).clear();
    } catch (Exception e) {
    }

    // 3. add property to old
    try {
      Iterator<?> pIter = sub.getPropertyIterator();
      while (pIter.hasNext()) {
        Property p = (Property) pIter.next();
        parent.addProperty(p);
      }
    } catch (Exception e) {
    }
    logger.info("{} replace {}.", sub.getClassName(), parent.getClassName());
  }
}
