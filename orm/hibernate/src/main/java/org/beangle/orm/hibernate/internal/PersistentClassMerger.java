package org.beangle.orm.hibernate.internal;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.MappedSuperclass;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
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

  // PersistentClass private list field
  private static Field subPropertyField = getField("subclassProperties");
  private static Field declarePropertyField = getField("declaredProperties");
  private static Field subclassField = getField("subclasses");;

  private static final boolean mergeSupport = (null != subPropertyField) && (null != declarePropertyField)
      && (null != subclassField);

  private static Field getField(String name) {
    try {
      Field field = PersistentClass.class.getDeclaredField(name);
      field.setAccessible(true);
      return field;
    } catch (Exception e) {
      logger.error("Cannot access PersistentClass " + name + " field ,Override Mapping will be disabled", e);
    }
    return null;
  }

  public static void merge(PersistentClass sub, PersistentClass parent) {
    if (!mergeSupport) throw new RuntimeException("Merge not supported!");

    String className = sub.getClassName();
    // 1. convert old to mappedsuperclass
    MappedSuperclass msc = new MappedSuperclass(parent.getSuperMappedSuperclass(), null);
    msc.setMappedClass(parent.getMappedClass());

    // 2.clear old subclass property
    parent.setSuperMappedSuperclass(msc);
    parent.setClassName(className);
    parent.setProxyInterfaceName(className);
    if (parent instanceof RootClass) {
      ((RootClass) parent).setDiscriminator(null);
      ((RootClass) parent).setPolymorphic(false);
    }
    try {
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

  public static boolean isMergesupport() {
    return mergeSupport;
  }

}
