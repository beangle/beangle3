package org.beangle.orm.hibernate.tool;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.ToOne;
import org.hibernate.mapping.Value;

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

  public void gen(String file) throws Exception {
    hbconfig = ConfigBuilder.build();
    Dialect dialect = new Oracle10gDialect();
    hbconfig.getProperties().put(Environment.DIALECT, dialect);
    Mapping mapping = hbconfig.buildMapping();
    freemarkerConfig = new freemarker.template.Configuration();
    freemarkerConfig.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

    Iterator<PersistentClass> iter = hbconfig.getClassMappings();
    List<PersistentClass> pcs = CollectUtils.newArrayList();
    while (iter.hasNext()) {
      PersistentClass pc = iter.next();
      // if (pc.getEntityName().equals("org.beangle.ems.rule.model.RuleConfig")) {
      // Property p = pc.getProperty("params");
      // Value v = p.getValue();
      // System.out.println(pc);
      // }
      if (!pc.getClassName().contains(".example.")) {
        pcs.add(pc);
      }
    }
    Map<String, Object> data = CollectUtils.newHashMap();
    data.put("classes", pcs);
    data.put("generator", this);
    Template freemarkerTemplate = freemarkerConfig.getTemplate("/hbm.ftl");
    FileWriter fw = new FileWriter("/tmp/hibernate.hbm.xml");
    freemarkerTemplate.process(data, fw);
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

  public static void main(String[] args) throws Exception {
    new HbmGenerator().gen("/tmp");
  }
}
