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
package org.beangle.orm.hibernate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.*;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.dao.query.LimitQuery;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.ModelMeta;
import org.beangle.commons.lang.Arrays;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.internal.SessionHolder;
import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 */
public class HibernateEntityDao implements EntityDao {

  /** Logger available to subclasses */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected SessionFactory sessionFactory;

  protected ModelMeta modelMeta;

  protected Session getSession() {
    SessionHolder holder = SessionUtils.currentSession(sessionFactory);
    return null != holder ? holder.getSession() : null;
  }

  @SuppressWarnings({ "unchecked" })
  public <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id) {
    return (T) get(modelMeta.getEntityType(clazz).getEntityName(), id);
  }

  @SuppressWarnings({ "unchecked" })
  public <T> T get(String entityName, Serializable id) {
    if (Strings.contains(entityName, '.')) {
      return (T) getSession().get(entityName, id);
    } else {
      String hql = "from " + entityName + " where id =:id";
      Query query = getSession().createQuery(hql);
      query.setParameter("id", id);
      List<?> rs = query.list();
      if (rs.isEmpty()) {
        return null;
      } else {
        return (T) rs.get(0);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends Entity<?>> List<T> getAll(Class<T> clazz) {
    String hql = "from " + modelMeta.getEntityType(clazz).getEntityName();
    Query query = getSession().createQuery(hql);
    query.setCacheable(true);
    return query.list();
  }

  public <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> clazz, ID[] values) {
    return get(clazz, "id", (Object[]) values);
  }

  public <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> clazz, Collection<ID> values) {
    return get(clazz, "id", values.toArray());
  }

  public <T extends Entity<?>> List<T> get(Class<T> entityClass, String keyName, Object... values) {
    if (entityClass == null || Strings.isEmpty(keyName) || values == null || values.length == 0) { return Collections
        .emptyList(); }
    String entityName = modelMeta.getEntityType(entityClass).getEntityName();
    return get(entityName, keyName, values);
  }

  public <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Collection<?> values) {
    if (clazz == null || Strings.isEmpty(keyName) || values == null || values.isEmpty()) { return Collections
        .emptyList(); }
    String entityName = modelMeta.getEntityType(clazz).getEntityName();
    return get(entityName, keyName, values.toArray());
  }

  public <T> List<T> get(String entityName, String keyName, Object... values) {
    StringBuilder hql = new StringBuilder();
    hql.append("select entity from ").append(entityName).append(" as entity where entity.").append(keyName)
        .append(" in (:keyName)");
    Map<String, Object> parameterMap = CollectUtils.newHashMap();
    if (values.length < 500) {
      parameterMap.put("keyName", values);
      QueryBuilder<T> query = OqlBuilder.hql(hql.toString());
      return search(query.params(parameterMap).build());
    } else {
      QueryBuilder<T> query = OqlBuilder.hql(hql.toString());
      List<T> rs = CollectUtils.newArrayList();
      int i = 0;
      while (i < values.length) {
        int end = i + 500;
        if (end > values.length) end = values.length;
        parameterMap.put("keyName", Arrays.subarray(values, i, end));
        rs.addAll(search(query.params(parameterMap).build()));
        i += 500;
      }
      return rs;
    }
  }

  public <T extends Entity<?>> List<T> get(Class<T> clazz, String[] attrs, Object... values) {
    Map<String, Object> params = CollectUtils.newHashMap();
    for (int i = 0; i < attrs.length; i++) {
      params.put(attrs[i], values[i]);
    }
    return get(clazz, params);
  }

  /**
   * @param clazz
   * @param parameterMap
   * @return data list
   */
  public <T extends Entity<?>> List<T> get(Class<T> clazz, final Map<String, Object> parameterMap) {
    if (clazz == null || parameterMap == null || parameterMap.isEmpty()) { return Collections.emptyList(); }
    String entityName = clazz.getName();
    StringBuilder hql = new StringBuilder();
    hql.append("select entity from ").append(entityName).append(" as entity ").append(" where ");

    Map<String, Object> m = new HashMap<String, Object>(parameterMap.keySet().size());
    // 变量编号
    int i = 0;
    for (final String keyName : parameterMap.keySet()) {
      if (Strings.isEmpty(keyName)) { return null; }
      i++;
      Object keyValue = parameterMap.get(keyName);

      String[] tempName = Strings.split(keyName, "\\.");
      String name = tempName[tempName.length - 1] + i;
      m.put(name, keyValue);

      if (keyValue != null && (keyValue.getClass().isArray() || keyValue instanceof Collection<?>)) {
        hql.append("entity.").append(keyName).append(" in (:").append(name).append(") and ");
      } else {
        hql.append("entity.").append(keyName).append(" = :").append(name).append(" and ");
      }
    }
    if (i > 0) hql.delete(hql.length() - " and ".length(), hql.length());
    return search(hql.toString(), m);
  }

  /**
   * 依据自构造的查询语句进行查询
   *
   * @see #buildCountQueryStr(Query)
   * @see org.beangle.commons.collection.page.Page
   */
  public <T> List<T> search(org.beangle.commons.dao.query.Query<T> query) {
    if (query instanceof LimitQuery) {
      org.beangle.commons.dao.query.LimitQuery<T> limitQuery = (org.beangle.commons.dao.query.LimitQuery<T>) query;
      if (null == limitQuery.getLimit()) {
        return QuerySupport.find(limitQuery, getSession());
      } else {
        return new SinglePage<T>(limitQuery.getLimit().getPageNo(), limitQuery.getLimit().getPageSize(),
            QuerySupport.count(limitQuery, getSession()), QuerySupport.find(query, getSession()));
      }
    } else {
      return QuerySupport.find(query, getSession());
    }
  }

  /**
   * 查询hql语句
   *
   * @param <T>
   * @param builder
   * @return data list
   */
  public <T> List<T> search(QueryBuilder<T> builder) {
    return (List<T>) search(builder.build());
  }

  @SuppressWarnings("unchecked")
  public <T> T uniqueResult(QueryBuilder<T> builder) {
    List<?> list = search(builder.build());
    if (list.isEmpty()) {
      return null;
    } else if (list.size() == 1) {
      return (T) list.get(0);
    } else {
      throw new RuntimeException("not unique query" + builder);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> search(String query, Object... params) {
    return (List<T>) QuerySupport.setParameter(getNamedOrCreateQuery(query), params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> search(String queryString, Map<String, Object> params) {
    return QuerySupport.setParameter(getNamedOrCreateQuery(queryString), params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> search(String queryString, final Map<String, Object> params, PageLimit limit,
      boolean cacheable) {
    Query query = getNamedOrCreateQuery(queryString);
    query.setCacheable(cacheable);
    if (null == limit) return QuerySupport.setParameter(query, params).list();
    else return paginateQuery(query, params, limit);
  }

  /**
   * Support "@named-query" or "from object" styles query
   *
   * @param queryString
   * @return Hibernate query
   */
  private Query getNamedOrCreateQuery(String queryString) {
    if (queryString.charAt(0) == '@') return getSession().getNamedQuery(queryString.substring(1));
    else return getSession().createQuery(queryString);
  }

  public void evict(Object entity) {
    getSession().evict(entity);
  }

  public int executeUpdate(final String queryString, final Object... argument) {
    return QuerySupport.setParameter(getNamedOrCreateQuery(queryString), argument).executeUpdate();
  }

  public int[] executeUpdateRepeatly(final String queryString, final Collection<Object[]> arguments) {
    Query query = getNamedOrCreateQuery(queryString);
    int[] updates = new int[arguments.size()];
    int i = 0;
    for (Object[] params : arguments) {
      updates[i] = QuerySupport.setParameter(query, params).executeUpdate();
      i++;
    }
    return updates;
  }

  public int executeUpdate(final String queryString, final Map<String, Object> parameterMap) {
    return QuerySupport.setParameter(getNamedOrCreateQuery(queryString), parameterMap).executeUpdate();
  }

  public Blob createBlob(InputStream inputStream, int length) {
    return Hibernate.getLobCreator(getSession()).createBlob(inputStream, length);
  }

  public Blob createBlob(InputStream inputStream) {
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(inputStream.available());
      StreamUtils.copy(inputStream, buffer);
      return Hibernate.getLobCreator(getSession()).createBlob(buffer.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Clob createClob(String str) {
    return Hibernate.getLobCreator(getSession()).createClob(str);
  }

  public void refresh(Object entity) {
    getSession().refresh(entity);
  }

  @SuppressWarnings("unchecked")
  public <T> T initialize(T proxy) {
    if (proxy instanceof HibernateProxy) {
      LazyInitializer init = ((HibernateProxy) proxy).getHibernateLazyInitializer();
      if (null == init.getSession() || init.getSession().isClosed()) {
        proxy = (T) getSession().get(init.getEntityName(), init.getIdentifier());
      } else {
        Hibernate.initialize(proxy);
      }
    } else if (proxy instanceof PersistentCollection) {
      Hibernate.initialize(proxy);
    }
    return proxy;
  }

  /**
   * @param query
   * @param params
   * @param limit
   * @return a page data
   */
  @SuppressWarnings("unchecked")
  private <T> Page<T> paginateQuery(Query query, Map<String, Object> params, PageLimit limit) {
    QuerySupport.setParameter(query, params);
    query.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(limit.getPageSize());
    List<T> targetList = query.list();
    String queryStr = buildCountQueryStr(query);
    Query countQuery = null;
    if (query instanceof SQLQuery) {
      countQuery = getSession().createSQLQuery(queryStr);
    } else {
      countQuery = getSession().createQuery(queryStr);
    }
    QuerySupport.setParameter(countQuery, params);
    // 返回结果
    return new SinglePage<T>(limit.getPageNo(), limit.getPageSize(),
        ((Number) (countQuery.uniqueResult())).intValue(), targetList);
  }

  public void saveOrUpdate(Object... entities) {
    if (null == entities) return;
    for (Object entity : entities) {
      if (entity instanceof Collection<?>) {
        for (Object elementEntry : (Collection<?>) entity) {
          persistEntity(elementEntry, null);
        }
      } else {
        persistEntity(entity, null);
      }
    }
  }

  public void save(Object... entities) {
    if (null == entities) return;
    for (Object entity : entities) {
      if (entity instanceof Collection<?>) {
        for (Object elementEntry : (Collection<?>) entity) {
          saveEntity(elementEntry, null);
        }
      } else {
        saveEntity(entity, null);
      }
    }
  }

  public void execute(Operation... opts) {
    for (Operation operation : opts) {
      switch (operation.type) {
      case SAVE_UPDATE:
        persistEntity(operation.data, null);
        break;
      case REMOVE:
        remove(operation.data);
        break;
      }
    }
  }

  public void execute(Operation.Builder builder) {
    for (Operation operation : builder.build()) {
      switch (operation.type) {
      case SAVE_UPDATE:
        persistEntity(operation.data, null);
        break;
      case REMOVE:
        remove(operation.data);
        break;
      }
    }
  }

  public void saveOrUpdate(Collection<?> entities) {
    if (null != entities && !entities.isEmpty()) {
      for (Object entity : entities) {
        persistEntity(entity, null);
      }
    }
  }

  private void saveEntity(Object entity, String entityName) {
    if (null == entity) return;
    if (null != entityName) {
      getSession().save(entityName, entity);
    } else {
      if (entity instanceof HibernateProxy) {
        getSession().save(entity);
      } else {
        getSession().save(modelMeta.getEntityType(entity.getClass()).getEntityName(), entity);
      }
    }
  }

  private void persistEntity(Object entity, String entityName) {
    if (null == entity) return;
    if (null != entityName) {
      getSession().saveOrUpdate(entityName, entity);
    } else {
      if (entity instanceof HibernateProxy) {
        getSession().saveOrUpdate(entity);
      } else {
        getSession().saveOrUpdate(modelMeta.getEntityType(entity.getClass()).getEntityName(), entity);
      }
    }
  }

  public void saveOrUpdate(String entityName, Collection<?> entities) {
    if (null != entities && !entities.isEmpty()) {
      for (Object entity : entities) {
        persistEntity(entity, entityName);
      }
    }
  }

  public void saveOrUpdate(String entityName, Object... entities) {
    if (null == entities) return;
    for (Object entity : entities) {
      persistEntity(entity, entityName);
    }
  }

  public int update(Class<?> entityClass, String attr, Object[] values, String[] argumentName,
      Object[] argumentValue) {
    if (null == values || values.length == 0) { return 0; }
    Map<String, Object> updateParams = CollectUtils.newHashMap();
    for (int i = 0; i < argumentValue.length; i++) {
      updateParams.put(argumentName[i], argumentValue[i]);
    }
    return update(entityClass, attr, values, updateParams);

  }

  public int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams) {
    if (null == values || values.length == 0 || updateParams.isEmpty()) { return 0; }
    String entityName = entityClass.getName();
    StringBuilder hql = new StringBuilder();
    hql.append("update ").append(entityName).append(" set ");
    Map<String, Object> newParams = CollectUtils.newHashMap();
    for (final String parameterName : updateParams.keySet()) {
      if (null == parameterName) {
        continue;
      }
      String locateParamName = Strings.replace(parameterName, ".", "_");
      hql.append(parameterName).append(" = ").append(":").append(locateParamName).append(",");
      newParams.put(locateParamName, updateParams.get(locateParamName));
    }
    hql.deleteCharAt(hql.length() - 1);
    hql.append(" where ").append(attr).append(" in (:ids)");
    newParams.put("ids", values);
    return executeUpdate(hql.toString(), newParams);
  }

  public void remove(Collection<?> entities) {
    if (null == entities || entities.isEmpty()) return;
    for (Object entity : entities)
      if (null != entity) getSession().delete(entity);
  }

  public void remove(Object... entities) {
    for (Object entity : entities) {
      if (null != entity) {
        if (entity instanceof Collection<?>) {
          for (Object innerEntity : (Collection<?>) entity) {
            getSession().delete(innerEntity);
          }
        } else {
          getSession().delete(entity);
        }
      }
    }
  }

  public boolean remove(Class<?> clazz, String attr, Object... values) {
    if (clazz == null || Strings.isEmpty(attr) || values == null || values.length == 0) { return false; }
    String entityName = modelMeta.getEntityType(clazz).getEntityName();
    StringBuilder hql = new StringBuilder();
    hql.append("delete from ").append(entityName).append(" where ").append(attr).append(" in (:ids)");
    Map<String, Object> parameterMap = CollectUtils.newHashMap();
    parameterMap.put("ids", values);
    return executeUpdate(hql.toString(), parameterMap) > 0;
  }

  public boolean remove(Class<?> entityClass, String attr, Collection<?> values) {
    return remove(entityClass, attr, values.toArray());
  }

  public boolean remove(Class<?> clazz, Map<String, Object> keyMap) {
    if (clazz == null || keyMap == null || keyMap.isEmpty()) { return false; }
    String entityName = modelMeta.getEntityType(clazz).getEntityName();
    StringBuilder hql = new StringBuilder();
    hql.append("delete from ").append(entityName).append(" where ");
    Set<String> keySet = keyMap.keySet();
    Map<String, Object> params = CollectUtils.newHashMap();
    for (final String keyName : keySet) {
      Object keyValue = keyMap.get(keyName);
      String paramName = keyName.replace('.', '_');
      params.put(paramName, keyValue);
      if (keyValue.getClass().isArray() || keyValue instanceof Collection<?>) {
        hql.append(keyName).append(" in (:").append(paramName).append(") and ");
      } else {
        hql.append(keyName).append(" = :").append(paramName).append(" and ");
      }
    }
    hql.append(" (1=1) ");
    return (executeUpdate(hql.toString(), params) > 0);
  }

  public long count(String entityName, String keyName, Object value) {
    String hql = "select count(*) from " + entityName + " where " + keyName + "=:value";
    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("value", value);
    List<?> rs = search(hql, params);
    if (rs.isEmpty()) {
      return 0;
    } else {
      return ((Number) rs.get(0)).longValue();
    }
  }

  public long count(Class<?> entityClass, String keyName, Object value) {
    return count(entityClass.getName(), keyName, value);
  }

  public long count(Class<?> entityClass, String[] attrs, Object[] values, String countAttr) {
    Assert.isTrue(null != attrs && null != values && attrs.length == values.length);

    String entityName = entityClass.getName();
    StringBuilder hql = new StringBuilder();
    if (Strings.isNotEmpty(countAttr)) {
      hql.append("select count(distinct ").append(countAttr).append(") from ");
    } else {
      hql.append("select count(*) from ");
    }
    hql.append(entityName).append(" as entity where ");
    Map<String, Object> params = CollectUtils.newHashMap();
    for (int i = 0; i < attrs.length; i++) {
      if (Strings.isEmpty(attrs[i])) {
        continue;
      }
      String keyName = Strings.replace(attrs[i], ".", "_");
      Object keyValue = values[i];
      params.put(keyName, keyValue);
      if (keyValue != null && (keyValue.getClass().isArray() || keyValue instanceof Collection<?>)) {
        hql.append("entity.").append(attrs[i]).append(" in (:").append(keyName).append(')');
      } else {
        hql.append("entity.").append(attrs[i]).append(" = :").append(keyName);
      }
      if (i < attrs.length - 1) hql.append(" and ");
    }
    return ((Number) search(hql.toString(), params).get(0)).longValue();
  }

  public boolean exist(Class<?> entityClass, String attr, Object value) {
    return count(entityClass, attr, value) > 0;
  }

  public boolean exist(String entityName, String attr, Object value) {
    return count(entityName, attr, value) > 0;
  }

  public boolean exist(Class<?> entity, String[] attrs, Object[] values) {
    return (count(entity, attrs, values, null) > 0);
  }

  /**
   * 检查持久化对象是否存在
   *
   * @param clazz
   * @param id
   * @param codeName
   * @param codeValue
   * @return boolean(是否存在) 如果entityId为空或者有不一样的entity存在则认为存在。
   */
  public boolean duplicate(Class<? extends Entity<?>> clazz, Serializable id, String codeName,
      Object codeValue) {
    if (null != codeValue && Strings.isNotEmpty(codeValue.toString())) {
      List<? extends Entity<?>> list = get(clazz, codeName, new Object[] { codeValue });
      if (list != null && !list.isEmpty()) {
        if (id == null) {
          return true;
        } else {
          for (Iterator<? extends Entity<?>> it = list.iterator(); it.hasNext();) {
            Entity<?> info = it.next();
            if (!info.getId().equals(id)) return true;
          }
          return false;
        }
      }
    }
    return false;
  }

  public boolean duplicate(String entityName, Serializable id, Map<String, Object> params) {
    StringBuilder b = new StringBuilder("from ");
    b.append(entityName).append(" where (1=1)");
    Map<String, Object> paramsMap = CollectUtils.newHashMap();
    int i = 0;
    for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); i++) {
      String key = iterator.next();
      b.append(" and ").append(key).append('=').append(":param" + i);
      paramsMap.put("param" + i, params.get(key));
    }
    List<?> list = search(b.toString(), paramsMap);
    if (!list.isEmpty()) {
      if (null == id) {
        return false;
      } else {
        for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
          Entity<?> one = (Entity<?>) iter.next();
          if (!one.getId().equals(id)) return false;
        }
      }
    }
    return true;
  }

  /**
   * 构造查询记录数目的查询字符串
   *
   * @param query
   * @return query string
   */
  private String buildCountQueryStr(Query query) {
    String queryStr = "select count(*) ";
    if (query instanceof SQLQuery) {
      queryStr += "from (" + query.getQueryString() + ")";
    } else {
      String lowerCaseQueryStr = query.getQueryString().toLowerCase();
      String selectWhich = lowerCaseQueryStr.substring(0, query.getQueryString().indexOf("from"));
      int indexOfDistinct = selectWhich.indexOf("distinct");
      int indexOfFrom = lowerCaseQueryStr.indexOf("from");
      // 如果含有distinct
      if (-1 != indexOfDistinct) {
        if (Strings.contains(selectWhich, ",")) {
          queryStr = "select count("
              + query.getQueryString().substring(indexOfDistinct, query.getQueryString().indexOf(",")) + ")";
        } else {
          queryStr = "select count(" + query.getQueryString().substring(indexOfDistinct, indexOfFrom) + ")";
        }
      }
      queryStr += query.getQueryString().substring(indexOfFrom);
    }
    return queryStr;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public static final class QuerySupport {
    private QuerySupport() {
      super();
    }

    private static Query buildHibernateQuery(org.beangle.commons.dao.query.Query<?> bquery,
        final Session session) {
      Query hibernateQuery = null;
      if (bquery.getLang().equals(org.beangle.commons.dao.query.Lang.HQL)) {
        hibernateQuery = session.createQuery(bquery.getStatement());
      } else {
        hibernateQuery = session.createSQLQuery(bquery.getStatement());
      }
      if (bquery.isCacheable()) hibernateQuery.setCacheable(bquery.isCacheable());
      setParameter(hibernateQuery, bquery.getParams());
      return hibernateQuery;
    }

    /**
     * 统计该查询的记录数
     *
     * @param limitQuery
     * @param hibernateSession
     * @return data count
     */
    public static int count(final org.beangle.commons.dao.query.LimitQuery<?> limitQuery,
        final Session hibernateSession) {
      final org.beangle.commons.dao.query.Query<?> cntQuery = limitQuery.getCountQuery();
      if (null == cntQuery) {
        Query hibernateQuery = buildHibernateQuery(limitQuery, hibernateSession);
        return hibernateQuery.list().size();
      } else {
        Query hibernateQuery = buildHibernateQuery(cntQuery, hibernateSession);
        final Number count = (Number) (hibernateQuery.uniqueResult());
        if (null == count) {
          return 0;
        } else {
          return count.intValue();
        }
      }
    }

    /**
     * 查询结果集
     *
     * @param query
     * @param session
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> find(final org.beangle.commons.dao.query.Query<T> query, final Session session) {
      if (query instanceof LimitQuery<?>) {
        LimitQuery<T> limitQuery = (LimitQuery<T>) query;
        Query hibernateQuery = buildHibernateQuery(limitQuery, session);
        if (null == limitQuery.getLimit()) {
          return hibernateQuery.list();
        } else {
          final PageLimit limit = limitQuery.getLimit();
          hibernateQuery.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
              limit.getPageSize());
          return hibernateQuery.list();
        }
      } else {
        return buildHibernateQuery(query, session).list();
      }
    }

    /**
     * 为query设置JPA style参数
     *
     * @param query
     * @param argument
     */
    public static Query setParameter(final Query query, final Object[] argument) {
      if (argument != null && argument.length > 0) {
        for (int i = 0; i < argument.length; i++)
          query.setParameter(String.valueOf(i + 1), argument[i]);
      }
      return query;
    }

    /**
     * 为query设置参数
     *
     * @param query
     * @param parameterMap
     * @return query
     */
    public static Query setParameter(final Query query, final Map<String, Object> parameterMap) {
      if (parameterMap != null && !parameterMap.isEmpty()) {
        for (final Iterator<String> ite = parameterMap.keySet().iterator(); ite.hasNext();) {
          final String parameterName = ite.next();
          if (null == parameterName) {
            break;
          }
          final Object parameterValue = parameterMap.get(parameterName);
          if (null == parameterValue) {
            query.setParameter(parameterName, (Object) null);
          } else if (parameterValue.getClass().isArray()) {
            query.setParameterList(parameterName, (Object[]) parameterValue);
          } else if (parameterValue instanceof Collection<?>) {
            query.setParameterList(parameterName, (Collection<?>) parameterValue);
          } else {
            query.setParameter(parameterName, parameterValue);
          }
        }
      }
      return query;
    }

    /**
     * 针对查询条件绑定查询的值
     *
     * @param query
     * @param conditions
     */
    public static void bindValues(final Query query, final List<Condition> conditions) {
      int position = 0;
      boolean hasInterrogation = false; // 含有问号
      for (final Iterator<Condition> iter = conditions.iterator(); iter.hasNext();) {
        final Condition condition = (Condition) iter.next();
        if (Strings.contains(condition.getContent(), "?")) {
          hasInterrogation = true;
        }
        if (hasInterrogation) {
          for (final Iterator<?> iterator = condition.getParams().iterator(); iterator.hasNext();) {
            query.setParameter(position++, iterator.next());
          }
        } else {
          final List<String> paramNames = condition.getParamNames();
          for (int i = 0; i < paramNames.size(); i++) {
            final String name = paramNames.get(i);
            final Object value = condition.getParams().get(i);
            if (value.getClass().isArray()) {
              query.setParameterList(name, (Object[]) value);
            } else if (value instanceof Collection<?>) {
              query.setParameterList(name, (Collection<?>) value);
            } else {
              query.setParameter(name, value);
            }
          }
        }
      }
    }
  }

  public void setModelMeta(ModelMeta modelMeta) {
    this.modelMeta = modelMeta;
  }

}
