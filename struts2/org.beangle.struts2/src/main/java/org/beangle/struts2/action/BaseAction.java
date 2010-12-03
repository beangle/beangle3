/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.text.TextResource;
import org.beangle.model.Entity;
import org.beangle.model.entity.Model;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.web.util.CookieUtils;
import org.beangle.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

public class BaseAction extends DispatchAction {

	protected static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected EntityDao entityDao;

	protected String getRemoteAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request) return null;
		return RequestUtils.getIpAddr(request);
	}

	/**
	 * @return requestUri
	 */
	public String getRequestURI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request) return null;
		return request.getRequestURI();
	}

	protected TextResource getTextResource() {
		return new ActionTextResource(this);
	}

	protected void put(String key, Object value) {
		ContextHelper.put(key, value);
	}

	protected Object[] getAll(String paramName) {
		return Params.getAll(paramName);
	}

	protected String get(String paramName) {
		return Params.get(paramName);
	}

	protected Object getAttribute(String name) {
		return ActionContext.getContext().getContextMap().get(name);
	}

	protected <T> T get(String name, Class<T> clazz) {
		return Params.get(name, clazz);
	}

	protected Boolean getBoolean(String name) {
		return Params.getBoolean(name);
	}

	protected boolean getBool(String name) {
		return Params.getBool(name);
	}

	protected java.sql.Date getDate(String name) {
		return Params.getDate(name);
	}

	protected Date getDateTime(String name) {
		return Params.getDateTime(name);
	}

	protected Float getFloat(String name) {
		return Params.getFloat(name);
	}

	protected Integer getInteger(String name) {
		return Params.getInteger(name);
	}

	protected Long getLong(String name) {
		return Params.getLong(name);
	}

	// populate------------------------------------------------------------------

	/**
	 * 将request中的参数设置到clazz对应的bean。
	 * 
	 * @param request
	 * @param clazz
	 * @param name
	 * @return
	 */
	protected <T> T populate(Class<T> clazz, String shortName) {
		return PopulateHelper.populate(clazz, shortName);
	}

	protected void populate(Object obj, String shortName) {
		Model.populate(Params.sub(shortName), obj);
	}

	protected Object populate(String entityName) {
		return PopulateHelper.populate(entityName);
	}

	protected Object populate(Class<?> clazz) {
		return PopulateHelper.populate(clazz);
	}

	protected Object populate(String entityName, String shortName) {
		return PopulateHelper.populate(entityName, shortName);
	}

	protected Object populate(Object obj, String entityName, String shortName) {
		return PopulateHelper.populate(obj, entityName, shortName);
	}

	protected void populate(Map<String, Object> params, Entity<?> entity, String entityName) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.getPopulator().populate(entity, entityName, params);
	}

	protected void populate(Map<String, Object> params, Entity<?> entity) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.populate(params, entity);
	}

	// query------------------------------------------------------
	protected int getPageNo() {
		return QueryHelper.getPageNo();
	}

	protected int getPageSize() {
		return QueryHelper.getPageSize();
	}

	/**
	 * 从request的参数或者cookie中(参数优先)取得分页信息
	 * 
	 * @param request
	 * @return
	 */
	protected PageLimit getPageLimit() {
		return QueryHelper.getPageLimit();
	}

	protected void populateConditions(OqlBuilder<?> builder) {
		QueryHelper.populateConditions(builder);
	}

	protected void populateConditions(OqlBuilder<?> builder, String exclusiveAttrNames) {
		QueryHelper.populateConditions(builder, exclusiveAttrNames);
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

	protected String getCookieValue(String cookieName) {
		return CookieUtils.getCookieValue(ServletActionContext.getRequest(), cookieName);
	}

	protected void addCookie(String name, String value, String path, int age) {
		try {
			CookieUtils.addCookie(ServletActionContext.getRequest(),
					ServletActionContext.getResponse(), name, value, path, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void addCookie(String name, String value, int age) {
		try {
			CookieUtils.addCookie(ServletActionContext.getRequest(),
					ServletActionContext.getResponse(), name, value, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void deleteCookie(String name) {
		CookieUtils.deleteCookieByName(ServletActionContext.getRequest(),
				ServletActionContext.getResponse(), name);
	}
}
