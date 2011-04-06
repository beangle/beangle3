/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.beangle.model.Entity;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.persist.GenericDao;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;

public abstract class GenericDaoImpl<T extends Entity<ID>, ID extends Serializable> implements
		GenericDao<T, ID> {

	protected EntityDao entityDao;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public T get(ID id) {
		return entityDao.get(getEntityClass(), id);
	}

	public List<T> get(ID[] ids) {
		OqlBuilder<T> query = OqlBuilder.from(getEntityClass(), "entity").where(
				new Condition("entity.id in(:gids)", ids));
		return entityDao.search(query);
	}

	public void saveOrUpdate(T entity) {
		entityDao.saveOrUpdate(entity);
	}

	public void saveOrUpdate(Collection<T> entitis) {
		entityDao.saveOrUpdate(entitis);
	}

	public void remove(Collection<T> entitis) {
		entityDao.remove(entitis);
	}

	public void remove(T entity) {
		entityDao.remove(entity);
	}

	public void remove(ID id) {
		entityDao.remove(get(id));
	}

	public void remove(ID[] ids) {
		entityDao.remove(get(ids));
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
