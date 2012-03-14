/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.beangle.dao.EntityDao;
import org.beangle.dao.query.QueryBuilder;
import org.beangle.util.meta.SystemVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

public class BaseAction extends DispatchAction {

	protected static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

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

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}
}
