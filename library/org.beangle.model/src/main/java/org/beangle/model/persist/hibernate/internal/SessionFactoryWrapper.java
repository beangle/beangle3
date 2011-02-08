/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.internal;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

@SuppressWarnings({ "deprecation", "serial" })
public class SessionFactoryWrapper implements SessionFactory {

	private SessionFactory cur;

	public SessionFactoryWrapper() {
		super();
	}

	public SessionFactoryWrapper(SessionFactory rawSf) {
		cur = rawSf;
	}

	public void setWrapped(SessionFactory cur) {
		this.cur = cur;
	}

	public void close() throws HibernateException {
		cur.close();
	}

	@SuppressWarnings("rawtypes")
	public void evict(Class persistentClass, Serializable id) throws HibernateException {
		cur.evict(persistentClass, id);
	}

	@SuppressWarnings("rawtypes")
	public void evict(Class persistentClass) throws HibernateException {
		cur.evict(persistentClass);
	}

	public void evictCollection(String roleName, Serializable id) throws HibernateException {
		cur.evictCollection(roleName, id);
	}

	public void evictCollection(String roleName) throws HibernateException {
		cur.evictCollection(roleName);
	}

	public void evictEntity(String entityName, Serializable id) throws HibernateException {
		cur.evictEntity(entityName, id);
	}

	public void evictEntity(String entityName) throws HibernateException {
		cur.evictEntity(entityName);
	}

	public void evictQueries() throws HibernateException {
		cur.evictQueries();
	}

	public void evictQueries(String cacheRegion) throws HibernateException {
		cur.evictQueries(cacheRegion);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAllClassMetadata() throws HibernateException {
		return cur.getAllClassMetadata();
	}

	@SuppressWarnings("rawtypes")
	public Map getAllCollectionMetadata() throws HibernateException {
		return cur.getAllCollectionMetadata();
	}

	@SuppressWarnings("rawtypes")
	public ClassMetadata getClassMetadata(Class persistentClass) throws HibernateException {
		return cur.getClassMetadata(persistentClass);
	}

	public ClassMetadata getClassMetadata(String entityName) throws HibernateException {
		return cur.getClassMetadata(entityName);
	}

	public CollectionMetadata getCollectionMetadata(String roleName) throws HibernateException {
		return cur.getCollectionMetadata(roleName);
	}

	public Session getCurrentSession() throws HibernateException {
		return cur.getCurrentSession();
	}

	@SuppressWarnings("rawtypes")
	public Set getDefinedFilterNames() {
		return cur.getDefinedFilterNames();
	}

	public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
		return cur.getFilterDefinition(filterName);
	}

	public Reference getReference() throws NamingException {
		return cur.getReference();
	}

	public Statistics getStatistics() {
		return cur.getStatistics();
	}

	public boolean isClosed() {
		return cur.isClosed();
	}

	public Session openSession() throws HibernateException {
		return cur.openSession();
	}

	public Session openSession(Connection connection, Interceptor interceptor) {
		return cur.openSession(connection, interceptor);
	}

	public Session openSession(Connection connection) {
		return cur.openSession(connection);
	}

	public Session openSession(Interceptor interceptor) throws HibernateException {
		return cur.openSession(interceptor);
	}

	public StatelessSession openStatelessSession() {
		return cur.openStatelessSession();
	}

	public StatelessSession openStatelessSession(Connection connection) {
		return cur.openStatelessSession(connection);
	}

	public boolean containsFetchProfileDefinition(String name) {
		return cur.containsFetchProfileDefinition(name);
	}

	public Cache getCache() {
		return cur.getCache();
	}

	public TypeHelper getTypeHelper() {
		return cur.getTypeHelper();
	}

}
