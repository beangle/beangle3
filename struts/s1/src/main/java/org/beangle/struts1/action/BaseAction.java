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
package org.beangle.struts1.action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.excel.ExcelItemWriter;
import org.beangle.commons.transfer.excel.ExcelTemplateWriter;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.commons.transfer.exporter.Exporter;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.commons.transfer.exporter.SimpleEntityExporter;
import org.beangle.commons.transfer.exporter.SimpleItemExporter;
import org.beangle.commons.transfer.exporter.TemplateExporter;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.struts1.dispatch.Conventions;
import org.beangle.struts1.support.QueryRequestSupport;
import org.beangle.struts1.support.StrutsMessageResource;

public class BaseAction extends DispatchAction {

  public BaseAction() {
  }

  public void remove(Collection arg0) {
    entityDao.remove(arg0);
  }

  public void saveOrUpdate(Collection arg0) {
    entityDao.saveOrUpdate(arg0);
  }

  public Collection search(OqlBuilder query) {
    return entityDao.search(query);
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public static Map getParams(HttpServletRequest request, String prefix) {
    return RequestUtils.getParams(request, prefix, null);
  }

  public static Map getParams(HttpServletRequest request, String prefix, String exclusiveAttrNames) {
    return RequestUtils.getParams(request, prefix, exclusiveAttrNames);
  }

  public static Long getLong(HttpServletRequest request, String name) {
    return RequestUtils.getLong(request, name);
  }

  public static String get(HttpServletRequest request, String name) {
    String v = request.getParameter(name);
    return (Strings.isBlank(v)) ? null : v;
  }

  public static Integer getInteger(HttpServletRequest request, String name) {
    return RequestUtils.getInteger(request, name);
  }

  public static Float getFloat(HttpServletRequest request, String name) {
    return RequestUtils.getFloat(request, name);
  }

  public static Boolean getBoolean(HttpServletRequest request, String name) {
    return RequestUtils.getBoolean(request, name);
  }

  public static void populate(Map params, Entity entity) {
    if (null == entity) {
      throw new RuntimeException("Cannot populate to null.");
    } else {
      EntityType type = Model.getType(entity.getClass());
      Model.getPopulator().populate(entity, type, params);
    }
  }

  public static Object populate(HttpServletRequest request, Class clazz) {
    return populate(request, clazz, EntityUtils.getCommandName(clazz));
  }

  public static Object populate(HttpServletRequest request, Class clazz, String name) {
    EntityType type = null;
    if (clazz.isInterface()) type = Model.getType(clazz.getName());
    else type = Model.getType(clazz);
    return populate(request, type.newInstance(), type, name);
  }

  public static Object populate(HttpServletRequest request, Object entity, String name) {
    return populate(request, entity, Model.getType(entity.getClass()), name);
  }

  public static Object populate(HttpServletRequest request, Object obj, EntityType type, String name) {
    return Model.getPopulator().populate(obj, type, RequestUtils.getParams(request, name));
  }

  protected Entity populateEntity(HttpServletRequest request, Class entityClass, String shortName) {
    Map params = getParams(request, shortName);
    Long entityId = getLong(request, shortName + ".id");
    Entity entity = null;
    try {
      if (null == entityId) {
        entity = (Entity) populate(request, entityClass, shortName);
        populate(params, entity);
        EntityUtils.evictEmptyProperty(entity);
      } else {
        entity = (Entity) entityDao.get(entityClass, entityId);
        params.remove("id");
        populate(params, entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return entity;
  }

  protected Entity getEntity(HttpServletRequest request, Class entityClass, String name) {
    Long entityId = getLong(request, name + ".id");
    if (null == entityId) {
      entityId = getLong(request, name + "Id");
    }
    Entity entity = null;
    try {
      EntityType type = Model.getType(entityClass);
      if (null == entityId) {
        entity = (Entity) populate(request, type.newInstance(), type, name);
      } else {
        entity = (Entity) entityDao.get(type.getEntityName(), entityId);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return entity;
  }

  protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response, String parameter) throws Exception {
    String method = request.getParameter(parameter);
    if (Strings.isNotEmpty(method)) return method;
    else return Conventions.getProfile(getClass()).getDefaultMethod();
  }

  protected Long getEntityId(HttpServletRequest request, String shortName) {
    Long entityId = getLong(request, shortName + ".id");
    if (null == entityId) entityId = getLong(request, shortName + "Id");
    return entityId;
  }

  protected Entity getModel(Class modelClass, Serializable id) {
    return entityDao.get(modelClass, id);
  }

  protected List getModels(Class modelClass, Long ids[]) {
    return entityDao.get(modelClass, "id", ids);
  }

  protected List getModels(Class modelClass, Object ids[]) {
    return entityDao.get(modelClass, "id", ids);
  }

  public static int getPageNo(HttpServletRequest request) {
    return QueryRequestSupport.getPageNo(request);
  }

  public static int getPageSize(HttpServletRequest request) {
    return QueryRequestSupport.getPageSize(request);
  }

  public static PageLimit getPageLimit(HttpServletRequest request) {
    return QueryRequestSupport.getPageLimit(request);
  }

  public static void addCollection(HttpServletRequest request, String name, Collection objs) {
    if (objs instanceof Page) QueryRequestSupport.addPage(request, objs);
    request.setAttribute(name, objs);
  }

  protected static void populateConditions(HttpServletRequest request, OqlBuilder entityQuery) {
    QueryRequestSupport.populateConditions(request, entityQuery);
  }

  protected static void populateConditions(HttpServletRequest request, OqlBuilder entityQuery,
      String exclusiveAttrNames) {
    QueryRequestSupport.populateConditions(request, entityQuery, exclusiveAttrNames);
  }

  public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String format = request.getParameter("format");
    String fileName = request.getParameter("fileName");
    String template = request.getParameter("template");
    if (Strings.isBlank(format)) format = "excel";
    if (Strings.isEmpty(fileName)) fileName = "exportResult";
    Context context = new Context();
    context.getDatas().put("format", format);
    context.getDatas().put("exportFile", fileName);
    if (Strings.isNotBlank(template)) {
      template = resolveTemplatePath(request, template);
      context.getDatas().put("templatePath", template);
    }
    configExportContext(request, context);
    Exporter exporter = getExporter(request, response, context);
    if (format.equals("excel")) {
      response.setContentType("application/vnd.ms-excel;charset=GBK");
      response.setHeader("Content-Disposition", "attachment;filename="
          + RequestUtils.encodeAttachName(request, context.getDatas().get("exportFile").toString()) + ".xls");
    } else if (format.equals("txt")) {
      response.setContentType("application/vnd.text;charset=GBK");
      response.setHeader("Content-Disposition", "attachment;filename="
          + RequestUtils.encodeAttachName(request, context.getDatas().get("exportFile").toString()) + ".txt");
    } else {
      throw new RuntimeException("Exporter is not supported for other format:" + exporter.getFormat());
    }
    exporter.setContext(context);
    exporter.transfer(new TransferResult());
    return null;
  }

  public ActionForward importForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    request.setAttribute("importAction", request.getRequestURI());
    return forward(request, "/pages/components/importData/form");
  }

  protected String resolveTemplatePath(HttpServletRequest request, String template) {
    return template;
  }

  protected Exporter getExporter(HttpServletRequest request, HttpServletResponse response, Context context)
      throws IOException {
    Collection datas = (Collection) context.getDatas().get("items");
    boolean isArray = false;
    if (null != datas && !datas.isEmpty()) {
      Object first = datas.iterator().next();
      if (first.getClass().isArray()) isArray = true;
    }
    Exporter exporter;
    context.put("keys", getExportKeys(request));
    String template = (String) context.getDatas().get("templatePath");
    if (isArray) {
      exporter = new SimpleItemExporter();
    } else if (Strings.isNotBlank(template)) {
      exporter = new TemplateExporter();
    } else {
      exporter = new SimpleEntityExporter();
      ((SimpleEntityExporter) exporter).setPropertyExtractor(getPropertyExtractor(request));
    }
    if (exporter instanceof SimpleItemExporter) {
      ((SimpleItemExporter) exporter).setTitles(Strings.split(getExportTitles(request), ","));
      exporter.setWriter(new ExcelItemWriter(response.getOutputStream()));
    } else {
      ExcelTemplateWriter writer = new ExcelTemplateWriter(response.getOutputStream());
      writer.setTemplate(new File(template).toURI().toURL());
      writer.setContext(context);
      exporter.setWriter(writer);
    }
    return exporter;
  }

  protected void configExportContext(HttpServletRequest request, Context context) {
    Collection datas = getExportDatas(request);
    context.getDatas().put("items", datas);
  }

  protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
    return new DefaultPropertyExtractor(getTextResource(request));
  }

  protected TextResource getTextResource(HttpServletRequest request) {
    return new StrutsMessageResource(getLocale(request), getResources(request));
  }

  protected Collection getExportDatas(HttpServletRequest request) {
    return Collections.EMPTY_LIST;
  }

  protected String getExportKeys(HttpServletRequest request) {
    String messageKey = request.getParameter("messageKey");
    if (Strings.isNotBlank(messageKey)) {
      MessageResources message = getResources(request, "excelconfig");
      return message.getMessage(messageKey);
    } else {
      return request.getParameter("keys");
    }
  }

  protected String getExportTitles(HttpServletRequest request) {
    String messageTitle = request.getParameter("messageTitle");
    if (Strings.isNotBlank(messageTitle)) {
      MessageResources message = getResources(request, "excelconfig");
      return message.getMessage(messageTitle);
    } else {
      String titles = request.getParameter("titles");
      return titles;
    }
  }

  protected MessageResources getResources(HttpServletRequest request, String key) {
    ServletContext context = request.getServletContext();
    ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request, context);
    return (MessageResources) context.getAttribute(key + moduleConfig.getPrefix());
  }

  protected EntityDao entityDao;
  protected static final String errorForward = "error";
}
