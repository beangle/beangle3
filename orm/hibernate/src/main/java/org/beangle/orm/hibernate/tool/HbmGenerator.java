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
package org.beangle.orm.hibernate.tool;

import java.io.FileWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.internal.OverrideConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.ToOne;
import org.hibernate.mapping.Value;
import org.hibernate.type.CustomType;
import org.hibernate.type.EnumType;
import org.hibernate.type.Type;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Template;

/**
 * 产生hbm文件
 * 
 * @author chaostone
 */
public class HbmGenerator {

  private Configuration hbconfig = null;
  private freemarker.template.Configuration freemarkerConfig;

  @SuppressWarnings("unchecked")
  public void gen(String file) throws Exception {
    hbconfig = new OverrideConfiguration();
    hbconfig.getProperties().put(Environment.DIALECT, new Oracle10gDialect());
    ConfigBuilder.build(hbconfig);
    freemarkerConfig = new freemarker.template.Configuration();
    freemarkerConfig.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

    Iterator<PersistentClass> iter = hbconfig.getClassMappings();
    List<PersistentClass> pcs = CollectUtils.newArrayList();
    while (iter.hasNext()) {
      PersistentClass pc = iter.next();
      Class<?> cls = pc.getMappedClass();
      Iterator<Property> pi = pc.getPropertyIterator();
      // For AnnotationBinder don't set column'length and nullable in ,let's we do it.
      while (pi.hasNext()) {
        Property p = pi.next();
        if (p.getColumnSpan() != 1) continue;
        Column column = (Column) p.getColumnIterator().next();
        if (column.getLength() == Column.DEFAULT_LENGTH) {
          Size size = findAnnotation(cls, Size.class, p.getName());
          if (null != size) column.setLength(size.max());
        }
        if (column.isNullable()) {
          NotNull notnull = findAnnotation(cls, NotNull.class, p.getName());
          if (null != notnull) column.setNullable(false);
        }
      }
      if (!pc.getClassName().contains(".example.")) pcs.add(pc);
    }
    Map<String, Object> data = CollectUtils.newHashMap();
    data.put("classes", pcs);
    data.put("generator", this);
    Template freemarkerTemplate = freemarkerConfig.getTemplate("/hbm.ftl");
    FileWriter fw = new FileWriter("/tmp/hibernate.hbm.xml");
    freemarkerTemplate.process(data, fw);
  }

  /**
   * find annotation on specified member
   * 
   * @param cls
   * @param annotationClass
   * @param name
   * @return
   */
  private <T extends Annotation> T findAnnotation(Class<?> cls, Class<T> annotationClass, String name) {
    Class<?> curr = cls;
    T ann = null;
    while (ann == null && curr != null && !curr.equals(Object.class)) {
      ann = findAnnotationLocal(curr, annotationClass, name);
      curr = curr.getSuperclass();
    }
    return ann;
  }

  private <T extends Annotation> T findAnnotationLocal(Class<?> cls, Class<T> annotationClass, String name) {
    T ann = null;
    try {
      Field field = cls.getDeclaredField(name);
      ann = field.getAnnotation(annotationClass);
    } catch (Exception e) {
    }
    if (null == ann) {
      Method method = null;
      try {
        method = cls.getMethod("get" + Strings.capitalize(name), new Class[] {});
        ann = method.getAnnotation(annotationClass);
      } catch (Exception e) {
      }
      if (null == ann && null == method) {
        try {
          method = cls.getMethod("is" + Strings.capitalize(name), new Class[] {});
          ann = method.getAnnotation(annotationClass);
        } catch (Exception e) {
        }
      }
    }
    return ann;
  }

  public boolean isToOne(Value value) {
    return value instanceof ToOne;
  }

  public boolean isOneToMany(Value value) {
    return value instanceof org.hibernate.mapping.OneToMany;
  }

  public boolean isManyToMany(Value value) {
    return value instanceof org.hibernate.mapping.ManyToOne;
  }

  public boolean isCollection(Value value) {
    return value instanceof org.hibernate.mapping.Collection;
  }

  public boolean isSet(Value value) {
    return value instanceof org.hibernate.mapping.Set;
  }

  public boolean isCustomType(Type type) {
    return type instanceof CustomType;
  }

  public boolean isEnumType(CustomType type) {
    return type.getUserType() instanceof EnumType;
  }

  public static void main(String[] args) throws Exception {
    new HbmGenerator().gen("/tmp");
  }
}
