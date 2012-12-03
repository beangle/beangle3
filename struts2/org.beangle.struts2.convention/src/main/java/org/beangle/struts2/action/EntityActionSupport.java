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
package org.beangle.struts2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.context.property.PropertyConfig;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TimeEntity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Enums;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.commons.transfer.exporter.Exporter;
import org.beangle.commons.transfer.exporter.ExporterFactory;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.beangle.commons.transfer.importer.IllegalImportFormatException;
import org.beangle.commons.transfer.importer.ImporterFactory;
import org.beangle.commons.transfer.importer.listener.ImporterForeignerListener;
import org.beangle.commons.transfer.io.TransferFormat;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.helper.Params;

/**
 * @author chaostone
 * @since 3.0.0
 */
public abstract class EntityActionSupport extends ActionSupport {

  protected EntityDao entityDao;

  protected PropertyConfig config;

  protected PropertyConfig getConfig() {
    return config;
  }

  // CURD----------------------------------------
  protected void remove(Collection<?> list) {
    entityDao.remove(list);
  }

  protected void remove(Object obj) {
    entityDao.remove(obj);
  }

  protected void saveOrUpdate(Collection<?> list) {
    entityDao.saveOrUpdate(list);
  }

  protected void saveOrUpdate(Object obj) {
    entityDao.saveOrUpdate(obj);
  }

  @SuppressWarnings("rawtypes")
  protected List search(QueryBuilder<?> query) {
    return entityDao.search(query);
  }

  protected EntityDao getEntityDao() {
    return entityDao;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  /**
   * 主页面
   */
  public String index() throws Exception {
    indexSetting();
    return forward();
  }

  /**
   * Seach Entitis
   */
  public String search() {
    put(getShortName() + "s", search(getQueryBuilder()));
    return forward();
  }

  protected Collection<?> getExportDatas() {
    return search(getQueryBuilder().limit(null));
  }

  /**
   * Edit by entity.id or id
   */
  public String edit() {
    Entity<?> entity = getEntity();
    put(getShortName(), entity);
    editSetting(entity);
    return forward();
  }

  /**
   * Remove entities by [entity.id]/entityIds
   */
  public String remove() throws Exception {
    Class<? extends Serializable> idclass = Model.getEntityType(getEntityName()).getIdType();
    Serializable entityId = getId(getShortName(), idclass);
    Collection<?> entities = null;
    if (null == entityId) {
      entities = getModels(getEntityName(), getIds(getShortName(), idclass));
    } else {
      Entity<?> entity = getModel(getEntityName(), entityId);
      entities = Collections.singletonList(entity);
    }
    return removeAndForward(entities);
  }

  /**
   * Save single entity
   */
  public String save() throws Exception {
    return saveAndForward(populateEntity());
  }

  protected Entity<?> populateEntity() {
    return populateEntity(getEntityName(), getShortName());
  }

  protected Entity<?> populateEntity(String entityName, String shortName) {
    Serializable entityId = getId(shortName, Model.getEntityType(entityName).getIdType());
    Entity<?> entity = null;
    if (null == entityId) {
      entity = (Entity<?>) populate(entityName, shortName);
    } else {
      entity = getModel(entityName, entityId);
      populate(Params.sub(shortName), entity, entityName);
    }
    return entity;
  }

  @SuppressWarnings("unchecked")
  protected <T> T populateEntity(Class<T> entityClass, String shortName) {
    EntityType type = null;
    if (entityClass.isInterface()) {
      type = Model.getEntityType(entityClass.getName());
    } else {
      type = Model.getEntityType(entityClass);
    }
    return (T) populateEntity(type.getEntityName(), shortName);
  }

  protected Entity<?> getEntity() {
    return getEntity(getEntityName(), getShortName());
  }

  protected Entity<?> getEntity(String entityName, String name) {
    EntityType type = Model.getEntityType(entityName);
    Serializable entityId = getId(name, type.getIdType());
    Entity<?> entity = null;
    try {
      if (null == entityId) entity = (Entity<?>) populate(type.newInstance(), type.getEntityName(), name);
      else entity = getModel(entityName, entityId);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return entity;
  }

  @SuppressWarnings("unchecked")
  protected <T> T getEntity(Class<T> entityClass, String shortName) {
    EntityType type = null;
    if (entityClass.isInterface()) {
      type = Model.getEntityType(entityClass.getName());
    } else {
      type = Model.getEntityType(entityClass);
    }
    return (T) getEntity(type.getEntityName(), shortName);
  }

  /**
   * 查看信息
   */
  public String info() throws Exception {
    Serializable entityId = getId(getShortName(), Model.getEntityType(getEntityName()).getIdType());
    if (null == entityId) {
      logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
    }
    Entity<?> entity = getModel(getEntityName(), entityId);
    put(getShortName(), entity);
    return forward();
  }

  protected void indexSetting() {

  }

  protected void editSetting(Entity<?> entity) {

  }

  /**
   * 保存对象
   * 
   * @param entity
   */
  protected String saveAndForward(Entity<?> entity) {
    try {
      if (entity instanceof TimeEntity) {
        TimeEntity timeEntity = (TimeEntity) entity;
        if (!entity.isPersisted()) timeEntity.setCreatedAt(new Date());
        timeEntity.setUpdatedAt(new Date());
      }
      saveOrUpdate(Collections.singletonList(entity));
      return redirect("search", "info.save.success");
    } catch (Exception e) {
      logger.info("saveAndForwad failure", e);
      return redirect("search", "info.save.failure");
    }

  }

  protected String removeAndForward(Collection<?> entities) {
    try {
      remove(entities);
    } catch (Exception e) {
      logger.info("removeAndForwad failure", e);
      return redirect("search", "info.delete.failure");
    }
    return redirect("search", "info.remove.success");
  }

  protected OqlBuilder<?> getQueryBuilder() {
    OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
    populateConditions(builder);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    return builder;
  }

  protected String getEntityName() {
    return null;
  }

  protected String getShortName() {
    String name = getEntityName();
    if (Strings.isNotEmpty(name)) return EntityUtils.getCommandName(name);
    else return null;
  }

  protected Entity<?> getModel(String entityName, Serializable id) {
    return (Entity<?>) entityDao.get(entityName, id);
  }

  @SuppressWarnings("rawtypes")
  protected List getModels(String entityName, Object[] ids) {
    return entityDao.get(entityName, "id", ids);
  }

  protected <T> List<T> getModels(Class<T> modelClass, Object[] ids) {
    return entityDao.get(modelClass, "id", ids);
  }

  /**
   * 导出数据
   * 
   * @throws Exception
   */
  public String export() throws Exception {
    TransferFormat format = Enums.get(TransferFormat.class, get("format", "Csv")).getOrElse(
        TransferFormat.Csv);
    String fileName = get("fileName");
    String template = get("template");
    if (Strings.isEmpty(fileName)) fileName = "exportResult";
    // 配置导出上下文
    Context context = new Context();
    context.put("format", format);
    context.put("exportFile", fileName);
    context.put("template", template);
    String properties = get("properties");
    if (null != properties) {
      String[] props = Strings.split(properties, ",");
      List<String> keys = CollectUtils.newArrayList();
      List<String> titles = CollectUtils.newArrayList();
      for (String prop : props) {
        if (prop.contains(":")) {
          keys.add(Strings.substringBefore(prop, ":"));
          titles.add(getTextInternal(Strings.substringAfter(prop, ":")));
        } else {
          keys.add(prop);
          titles.add(getTextInternal(getShortName() + "." + prop));
        }
      }
      context.put(Context.KEYS, Strings.join(keys, ","));
      context.put(Context.TITLES, Strings.join(titles, ","));
    } else {
      context.put(Context.KEYS, get("keys"));
      context.put(Context.TITLES, get("titles"));
    }
    context.put(Context.EXTRACTOR, getPropertyExtractor());

    HttpServletResponse response = ServletActionContext.getResponse();
    Exporter exporter = buildExporter(format, context);
    configExporter(exporter, context);
    if (format.equals(TransferFormat.Xls)) {
      response.setContentType("application/vnd.ms-excel;charset=GBK");
    } else {
      response.setContentType("application/x-msdownload");
    }
    response.setHeader(
        "Content-Disposition",
        "attachment;filename="
            + RequestUtils.encodeAttachName(ServletActionContext.getRequest(), fileName + "." + format));
    // 进行输出
    exporter.setContext(context);
    exporter.transfer(new TransferResult());
    return null;
  }

  protected PropertyExtractor getPropertyExtractor() {
    return new DefaultPropertyExtractor(getTextResource());
  }

  protected Exporter buildExporter(TransferFormat format, Context context) throws IOException {
    Exporter exporter = ExporterFactory.getExporter(format, context);
    HttpServletResponse response = ServletActionContext.getResponse();
    exporter.getWriter().setOutputStream(response.getOutputStream());
    return exporter;
  }

  protected void configExporter(Exporter exporter, Context context) throws IOException {
    context.put("items", getExportDatas());
  }

  public String importForm() {
    return forward("/components/importData/form");
  }

  /**
   * 构建实体导入者
   */
  protected EntityImporter buildEntityImporter() {
    if (null == getEntityName()) return buildEntityImporter("importFile", null);
    else return buildEntityImporter("importFile", Model.getEntityType(getEntityName()).getEntityClass());

  }

  /**
   * 用于构建单个实体类的导入构造器
   * 
   * @param clazz
   */
  protected EntityImporter buildEntityImporter(Class<?> clazz) {
    return buildEntityImporter("importFile", clazz);
  }

  /**
   * 构建实体导入者
   * 
   * @param upload
   * @param clazz
   */
  protected EntityImporter buildEntityImporter(String upload, Class<?> clazz) {
    try {
      File file = get(upload, File.class);
      if (null == file) {
        logger.error("cannot get upload file {}.", upload);
        return null;
      }
      String fileName = get(upload + "FileName");
      InputStream is = new FileInputStream(file);
      String formatName = Strings.capitalize(Strings.substringAfterLast(fileName, "."));
      Option<TransferFormat> format = Enums.get(TransferFormat.class, formatName);
      return (format.isDefined()) ? ImporterFactory.getEntityImporter(format.get(), is, clazz, null) : null;
    } catch (Exception e) {
      logger.error("error", e);
      return null;
    }
  }

  /**
   * 导入信息
   */
  public String importData() {
    TransferResult tr = new TransferResult();
    EntityImporter importer = buildEntityImporter();
    if (null == importer) { return forward("/components/importData/error"); }
    try {
      configImporter(importer);
      importer.transfer(tr);
      put("importer", importer);
      put("importResult", tr);
      if (tr.hasErrors()) {
        return forward("/components/importData/error");
      } else {
        return forward("/components/importData/result");
      }
    } catch (IllegalImportFormatException e) {
      tr.addFailure(getText("error.importformat"), e.getMessage());
      put("importResult", tr);
      return forward("/components/importData/error");
    }
  }

  protected void configImporter(EntityImporter importer) {
    for (final TransferListener il : getImporterListeners()) {
      importer.addListener(il);
    }
  }

  protected List<? extends TransferListener> getImporterListeners() {
    return Collections.singletonList(new ImporterForeignerListener(entityDao));
  }

  public void setConfig(PropertyConfig propertyConfig) {
    this.config = propertyConfig;
  }

}
