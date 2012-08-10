/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.pojo.TimeEntity;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.csv.CsvItemReader;
import org.beangle.commons.transfer.excel.ExcelItemReader;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.commons.transfer.exporter.Exporter;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.commons.transfer.importer.DefaultEntityImporter;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.beangle.commons.transfer.importer.listener.ImporterForeignerListener;
import org.beangle.commons.transfer.io.TransferFormats;
import org.beangle.commons.util.meta.SystemVersion;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts2.helper.ExportHelper;
import org.beangle.struts2.helper.Params;

public abstract class EntityActionSupport extends BaseAction {
  protected EntityDao entityDao;
  protected SystemVersion systemVersion;

  public SystemVersion getSystemVersion() {
    return systemVersion;
  }

  public void setSystemVersion(SystemVersion systemVersion) {
    this.systemVersion = systemVersion;
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
   * 
   * @return
   */
  public String index() throws Exception {
    indexSetting();
    return forward();
  }

  /**
   * 查找标准
   * 
   * @return
   */
  public String search() {
    put(getShortName() + "s", search(getQueryBuilder()));
    return forward();
  }

  protected Collection<?> getExportDatas() {
    return search(getQueryBuilder().limit(null));
  }

  /**
   * 修改标准
   * 
   * @return
   */
  public String edit() {
    Serializable entityId = getId(Model.getEntityType(getEntityName()).getIdClass(), getShortName());
    Entity<?> entity = null;
    if (null == entityId) {
      entity = populateEntity();
    } else {
      entity = getModel(getEntityName(), entityId);
    }
    put(getShortName(), entity);
    editSetting(entity);
    return forward();
  }

  /**
   * 删除
   * 
   * @return
   */
  public String remove() throws Exception {
    Class<? extends Serializable> idclass = Model.getEntityType(getEntityName()).getIdClass();
    Serializable entityId = getId(idclass, getShortName());
    Collection<?> entities = null;
    if (null == entityId) {
      entities = getModels(getEntityName(), getIds(idclass, getShortName()));
    } else {
      Entity<?> entity = getModel(getEntityName(), entityId);
      entities = Collections.singletonList(entity);
    }
    return removeAndForward(entities);
  }

  /**
   * 保存修改后的标准
   * 
   * @return
   */
  public String save() throws Exception {
    return saveAndForward(populateEntity());
  }

  protected Entity<?> populateEntity() {
    return populateEntity(getEntityName(), getShortName());
  }

  /**
   * @deprecated Use getId
   * @param shortName
   * @return
   */
  protected Long getEntityId(String shortName) {
    return getId(Long.class, shortName);
  }

  /**
   * @deprecated user getIds directly
   * @param shortName
   * @return
   */
  protected Long[] getEntityIds(String shortName) {
    return getIds(Long.class, shortName);
  }

  protected Entity<?> populateEntity(String entityName, String shortName) {
    Serializable entityId = getId(Model.getEntityType(getEntityName()).getIdClass(), shortName);
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
    Serializable entityId = getId(type.getIdClass(), name);
    Entity<?> entity = null;
    try {
      if (null == entityId) {
        entity = (Entity<?>) populate(type.newInstance(), type.getEntityName(), name);
      } else {
        entity = getModel(entityName, entityId);
      }
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
   * 
   * @return
   */
  public String info() throws Exception {
    Serializable entityId = getId(Model.getEntityType(getEntityName()).getIdClass(), getShortName());
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
   * @return
   */
  protected String saveAndForward(Entity<?> entity) {
    try {
      if (entity instanceof TimeEntity<?>) {
        TimeEntity<?> timeEntity = (TimeEntity<?>) entity;
        if (!timeEntity.isPersisted()) {
          timeEntity.setCreatedAt(new Date());
        }
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

  protected QueryBuilder<?> getQueryBuilder() {
    OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
    populateConditions(builder);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    return builder;
  }

  protected String getEntityName() {
    throw new RuntimeException(this.getClass() + " should override getEntityName");
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
   * @return
   * @throws Exception
   */
  public String export() throws Exception {
    String format = get("format");
    String fileName = get("fileName");
    String template = get("template");
    if (Strings.isBlank(format)) {
      format = TransferFormats.XLS;
    }
    if (Strings.isEmpty(fileName)) {
      fileName = "exportResult";
    }

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
    Exporter exporter = buildExporter(context);
    exporter.getWriter().setOutputStream(response.getOutputStream());
    configExporter(exporter, context);
    if (format.equals(TransferFormats.XLS)) {
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

  protected Exporter buildExporter(Context context) {
    return ExportHelper.buildExporter(context);
  }

  protected void configExporter(Exporter exporter, Context context) {
    configExportContext(context);
  }

  /**
   * @deprecated use configExporter
   * @param context
   */
  protected void configExportContext(Context context) {
    context.put("items", getExportDatas());
  }

  public String importForm() {
    return forward("/components/importData/form");
  }

  /**
   * 构建实体导入者
   * 
   * @return
   */
  protected EntityImporter buildEntityImporter() {
    if (null == getEntityName()) {
      return buildEntityImporter("importFile", null);
    } else {
      return buildEntityImporter("importFile", Model.getEntityType(getEntityName()).getEntityClass());
    }
  }

  /**
   * 用于构建单个实体类的导入构造器
   * 
   * @param clazz
   * @return
   */
  protected EntityImporter buildEntityImporter(Class<?> clazz) {
    return buildEntityImporter("importFile", clazz);
  }

  /**
   * 构建实体导入者
   * 
   * @param upload
   * @param clazz
   * @return
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
      if (fileName.endsWith(".xls")) {
        EntityImporter importer = (clazz == null) ? new DefaultEntityImporter() : new DefaultEntityImporter(
            clazz);
        importer.setReader(new ExcelItemReader(is, 1));
        put("importer", importer);
        return importer;
      } else {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        if (null == reader.readLine()) {
          reader.close();
          return null;
        }
        reader.reset();
        EntityImporter importer = (clazz == null) ? new DefaultEntityImporter() : new DefaultEntityImporter(
            clazz);
        importer.setReader(new CsvItemReader(reader));
        return importer;
      }
    } catch (Exception e) {
      logger.error("error", e);
      return null;
    }
  }

  /**
   * 导入信息
   * 
   * @return
   */
  public String importData() {
    TransferResult tr = new TransferResult();
    EntityImporter importer = buildEntityImporter();
    if (null == importer) { return forward("/components/importData/error"); }
    configImporter(importer);
    importer.transfer(tr);
    put("importResult", tr);
    if (tr.hasErrors()) {
      return forward("/components/importData/error");
    } else {
      return forward("/components/importData/result");
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
}
