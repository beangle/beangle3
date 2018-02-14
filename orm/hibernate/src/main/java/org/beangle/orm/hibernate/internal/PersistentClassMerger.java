/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.orm.hibernate.internal;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.mapping.Column;
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
    // 1. convert old to mapped superclass
    MappedSuperclass msc = new MappedSuperclass(parent.getSuperMappedSuperclass(), null);
    final Class<?> parentClass = parent.getMappedClass();
    msc.setMappedClass(parentClass);

    // 2.clear old subclass property
    parent.setSuperMappedSuperclass(msc);
    parent.setClassName(className);
    parent.setProxyInterfaceName(className);
    if (parent instanceof RootClass) {
      if (!parentClass.getSuperclass().isAnnotationPresent(Entity.class)) {
        ((RootClass) parent).setDiscriminator(null);
        ((RootClass) parent).setPolymorphic(false);
        @SuppressWarnings("unchecked")
        Iterator<Column> iter = parent.getTable().getColumnIterator();
        while (iter.hasNext()) {
          if (iter.next().getName().equalsIgnoreCase("dtype")) {
            iter.remove();
            break;
          }
        }
      }
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
