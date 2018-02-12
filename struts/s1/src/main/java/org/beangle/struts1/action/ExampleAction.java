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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.struts1.dispatch.Conventions;

public abstract class ExampleAction extends BaseAction {

  public ExampleAction() {
  }

  public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    indexSetting(request);
    return forward(request);
  }

  public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    addCollection(request, shortName + "s", search(buildQuery(request)));
    return forward(request);
  }

  protected Collection getExportDatas(HttpServletRequest request) {
    OqlBuilder query = buildQuery(request);
    query.limit(null);
    return search(query);
  }

  public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long entityId = getEntityId(request, shortName);
    Entity entity = null;
    if (null == entityId) entity = populateEntity(request);
    else entity = getModel(entityName, entityId);
    request.setAttribute(shortName, entity);
    editSetting(request, entity);
    return forward(request);
  }

  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long entityId = getLong(request, shortName + "Id");
    Collection entities = null;
    if (null == entityId) {
      String entityIdSeq = request.getParameter(shortName + "Ids");
      entities = getModels(entityName, Strings.transformToLong(Strings.split(entityIdSeq)));
    } else {
      Entity entity = getModel(entityName, entityId);
      entities = Collections.singletonList(entity);
    }
    return removeAndForward(request, entities);
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    return saveAndForward(request, populateEntity(request));
  }

  protected Entity populateEntity(HttpServletRequest request) {
    return populateEntity(request, entityName, shortName);
  }

  protected Long getEntityId(HttpServletRequest request, String shortName) {
    Long entityId = getLong(request, shortName + ".id");
    if (null == entityId) entityId = getLong(request, shortName + "Id");
    return entityId;
  }

  protected Entity populateEntity(HttpServletRequest request, String entityName, String shortName) {
    Long entityId = getEntityId(request, shortName);
    Entity entity = null;
    try {
      if (null == entityId) {
        entity = (Entity) populate(request, entityName, shortName);
      } else {
        java.util.Map params = getParams(request, shortName);
        entity = getModel(entityName, entityId);
        populate(params, entity, entityName);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return entity;
  }

  protected Entity populateEntity(HttpServletRequest request, Class entityClass, String shortName) {
    EntityType type = null;
    if (entityClass.isInterface()) type = Model.getType(entityClass.getName());
    else type = Model.getType(entityClass);
    return populateEntity(request, type.getEntityName(), shortName);
  }

  protected Entity getEntity(HttpServletRequest request) {
    return getEntity(request, entityName, shortName);
  }

  protected Entity getEntity(HttpServletRequest request, String entityName, String name) {
    Long entityId = getEntityId(request, name);
    Entity entity = null;
    try {
      EntityType type = Model.getType(entityName);
      if (null == entityId) entity = (Entity) populate(request, type.newInstance(), type, name);
      else entity = getModel(entityName, entityId);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return entity;
  }

  protected Entity getEntity(HttpServletRequest request, Class entityClass, String shortName) {
    EntityType type = null;
    if (entityClass.isInterface()) type = Model.getType(entityClass.getName());
    else type = Model.getType(entityClass);
    return getEntity(request, type.getEntityName(), shortName);
  }

  public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Long entityId = getEntityId(request, shortName);
    Entity entity = getModel(entityName, entityId);
    request.setAttribute(shortName, entity);
    return forward(request);
  }

  protected void indexSetting(HttpServletRequest httpservletrequest) throws Exception {
  }

  protected void editSetting(HttpServletRequest httpservletrequest, Entity entity1) throws Exception {
  }

  protected ActionForward saveAndForward(HttpServletRequest request, Entity entity) throws Exception {
    try {
      saveOrUpdate(Collections.singletonList(entity));
      return redirect(request, "search", "info.save.success");
    } catch (Exception e) {
      log.info("saveAndForwad failure for:" + e.getMessage());
      return redirect(request, "search", "info.save.failure");
    }
  }

  protected ActionForward removeAndForward(HttpServletRequest request, Collection entities) throws Exception {
    try {
      remove(entities);
    } catch (Exception e) {
      log.info("removeAndForwad failure for:" + e.getMessage());
      return redirect(request, "search", "info.delete.failure");
    }
    return redirect(request, "search", "info.delete.success");
  }

  protected OqlBuilder buildQuery(HttpServletRequest request) {
    OqlBuilder query = OqlBuilder.from(entityName, shortName);
    populateConditions(request, query);
    query.orderBy(Order.parse(request.getParameter("orderBy")));
    query.limit(getPageLimit(request));
    return query;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
    if (Strings.isEmpty(shortName)) shortName = EntityUtils.getCommandName(entityName);
  }

  protected Entity getModel(String entityName, Serializable id) {
    return entityDao.get(entityName, id);
  }

  protected List getModels(String entityName, Long ids[]) {
    return entityDao.get(entityName, "id", ids);
  }

  protected List getModels(Class modelClass, Object ids[]) {
    return entityDao.get(modelClass, "id", ids);
  }

  protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response, String parameter) throws Exception {
    String method = request.getParameter(parameter);
    if (Strings.isNotEmpty(method)) return method;
    else return Conventions.getProfile(getClass()).getDefaultMethod();
  }

  protected String entityName;
  protected String shortName;
}
