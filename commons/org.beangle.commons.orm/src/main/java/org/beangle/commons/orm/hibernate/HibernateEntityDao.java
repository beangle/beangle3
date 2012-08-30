/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
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
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.orm.hibernate.internal.SessionUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.LazyInitializer;

/**
 * @author chaostone
 */
public class HibernateEntityDao implements EntityDao {

  protected SessionFactory sessionFactory;

  protected Session getSession() {
    return SessionUtils.currentSession(sessionFactory);
  }

  @SuppressWarnings({ "unchecked" })
  public <T> T get(Class<T> clazz, Serializable id) {
    return (T) get(Model.getEntityType(clazz).getEntityName(), id);
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
  public <T> List<T> getAll(Class<T> clazz) {
    String hql = "from " + Model.getEntityType(clazz).getEntityName();
    Query query = getSession().createQuery(hql);
    query.setCacheable(true);
    return query.list();
  }

  public <T> List<T> get(Class<T> entityClass, Long... values) {
    return get(entityClass, "id", (Object[]) values);
  }

  public <T> List<T> get(Class<T> entityClass, Object... values) {
    return get(entityClass, "id", values);
  }

  public <T> List<T> get(Class<T> entityClass, Collection<?> values) {
    return get(entityClass, "id", values.toArray());
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> entityClass, String keyName, Object... values) {
    if (entityClass == null || Strings.isEmpty(keyName) || values == null || values.length == 0) { return Collections
        .emptyList(); }
    String entityName = Model.getEntityType(entityClass).getEntityName();
    return (List<T>) get(entityName, keyName, values);
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> entityClass, String keyName, Collection<?> values) {
    if (entityClass == null || Strings.isEmpty(keyName) || values == null || values.isEmpty()) { return Collections
        .emptyList(); }
    String entityName = Model.getEntityType(entityClass).getEntityName();
    return (List<T>) get(entityName, keyName, values.toArray());
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
        if (end > values.length) {
          end = values.length;
        }
        parameterMap.put("keyName", ArrayUtils.subarray(values, i, end));
        rs.addAll(search(query.params(parameterMap).build()));
        i += 500;
      }
      return rs;
    }
  }

  public <T> List<T> get(Class<T> entity, String[] attrs, Object... values) {
    Map<String, Object> params = CollectUtils.newHashMap();
    for (int i = 0; i < attrs.length; i++) {
      params.put(attrs[i], values[i]);
    }
    return get(entity, params);
  }

  /**
   * @param entity
   * @param parameterMap
   * @return
   */
  public <T> List<T> get(Class<T> entity, final Map<String, Object> parameterMap) {
    if (entity == null || parameterMap == null || parameterMap.isEmpty()) { return Collections.emptyList(); }
    String entityName = entity.getName();
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
    hql.append(" (1=1) ");
    @SuppressWarnings("unchecked")
    List<T> rs = (List<T>) searchHQLQuery(hql.toString(), m);
    return rs;
  }

  /**
   * 依据自构造的查询语句进行查询
   * 
   * @see #buildCountQueryStr(Query)
   * @see org.beangle.entity.query.limit.Pagination
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
   * @return
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
  public <T> List<T> searchNamedQuery(final String queryName, final Map<String, Object> params) {
    Query query = this.getSession().getNamedQuery(queryName);
    return QuerySupport.setParameter(query, params).list();
  }

  @SuppressWarnings({ "unchecked" })
  public <T> List<T> searchNamedQuery(final String queryName, final Map<String, Object> params,
      boolean cacheable) {
    Query query = getSession().getNamedQuery(queryName);
    query.setCacheable(cacheable);
    return QuerySupport.setParameter(query, params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> searchNamedQuery(String queryName, Object... params) {
    Query query = getSession().getNamedQuery(queryName);
    return QuerySupport.setParameter(query, params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> searchHQLQuery(String hql) {
    return getSession().createQuery(hql).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> searchHQLQuery(String hql, Map<String, Object> params) {
    Query query = getSession().createQuery(hql);
    return QuerySupport.setParameter(query, params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> searchHQLQuery(String hql, Object... params) {
    Query query = getSession().createQuery(hql);
    return (List<T>) QuerySupport.setParameter(query, params).list();
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> searchHQLQuery(String hql, final Map<String, Object> params, boolean cacheable) {
    Query query = getSession().createQuery(hql);
    query.setCacheable(cacheable);
    return QuerySupport.setParameter(query, params).list();
  }

  public <T> Page<T> paginateNamedQuery(String queryName, Map<String, Object> params, PageLimit limit) {
    Query query = getSession().getNamedQuery(queryName);
    return paginateQuery(query, params, limit);
  }

  public <T> Page<T> paginateHQLQuery(String hql, Map<String, Object> params, PageLimit limit) {
    Query query = getSession().createQuery(hql);
    return paginateQuery(query, params, limit);
  }

  public void evict(Object entity) {
    getSession().evict(entity);
  }

  public int executeUpdateHql(final String queryStr, final Object... argument) {
    Query query = getSession().createQuery(queryStr);
    return QuerySupport.setParameter(query, argument).executeUpdate();
  }

  public int executeUpdateHqlRepeatly(final String queryStr, final List<Object[]> arguments) {
    Query query = getSession().createQuery(queryStr);
    int updated = 0;
    for (Object[] params : arguments) {
      updated += QuerySupport.setParameter(query, params).executeUpdate();
    }
    return updated;
  }

  public int executeUpdateHql(final String queryStr, final Map<String, Object> parameterMap) {
    Query query = getSession().createQuery(queryStr);
    return QuerySupport.setParameter(query, parameterMap).executeUpdate();
  }

  public int executeUpdateNamedQuery(final String queryName, final Map<String, Object> parameterMap) {
    Query query = getSession().getNamedQuery(queryName);
    return QuerySupport.setParameter(query, parameterMap).executeUpdate();
  }

  public int executeUpdateNamedQuery(final String queryName, final Object... arguments) {
    Query query = getSession().getNamedQuery(queryName);
    return QuerySupport.setParameter(query, arguments).executeUpdate();
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
      getHibernateTemplate().initialize(proxy); 
    }
    return proxy;
  }

  /**
   * @param query
   * @param names
   * @param values
   * @param pageNo
   * @param pageSize
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> Page<T> paginateQuery(Query query, Map<String, Object> params, PageLimit limit) {
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
        getSession().save(Model.getEntityType(entity.getClass()).getEntityName(), entity);
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
        getSession().saveOrUpdate(Model.getEntityType(entity.getClass()).getEntityName(), entity);
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
    return executeUpdateHql(hql.toString(), newParams);
  }

  public void remove(Collection<?> entities) {
    if (null == entities || entities.isEmpty()) return;
    for (Object entity : entities)
      if (null != entity) getSession().delete(entity);
  }

  public void remove(Object... entities) {
    for (Object entity : entities) {
      if (null != entity) getSession().delete(entity);
    }
  }

  public boolean remove(Class<?> clazz, String attr, Object... values) {
    if (clazz == null || Strings.isEmpty(attr) || values == null || values.length == 0) { return false; }
    String entityName = Model.getEntityType(clazz).getEntityName();
    StringBuilder hql = new StringBuilder();
    hql.append("delete from ").append(entityName).append(" where ").append(attr).append(" in (:ids)");
    Map<String, Object> parameterMap = CollectUtils.newHashMap();
    parameterMap.put("ids", values);
    return executeUpdateHql(hql.toString(), parameterMap) > 0;
  }

  public boolean remove(Class<?> entityClass, String attr, Collection<?> values) {
    return remove(entityClass, attr, values.toArray());
  }

  public boolean remove(Class<?> clazz, Map<String, Object> keyMap) {
    if (clazz == null || keyMap == null || keyMap.isEmpty()) { return false; }
    String entityName = Model.getEntityType(clazz).getEntityName();
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
    return (executeUpdateHql(hql.toString(), params) > 0);
  }

  public long count(String entityName, String keyName, Object value) {
    String hql = "select count(*) from " + entityName + " where " + keyName + "=:value";
    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("value", value);
    List<?> rs = searchHQLQuery(hql, params);
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
    return ((Number) searchHQLQuery(hql.toString(), params).get(0)).longValue();
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
   * @param entityName
   * @param keyName
   * @param id
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
            if (!info.getIdentifier().equals(id)) { return true; }
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
    List<?> list = searchHQLQuery(b.toString(), paramsMap);
    if (!list.isEmpty()) {
      if (null == id) {
        return false;
      } else {
        for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
          Entity<?> one = (Entity<?>) iter.next();
          if (!one.getIdentifier().equals(id)) { return false; }
        }
      }
    }
    return true;
  }

  /**
   * 构造查询记录数目的查询字符串
   * 
   * @param query
   * @return
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
        final Session hibernateSession) {
      Query hibernateQuery = null;
      if (bquery.getLang().equals(org.beangle.commons.dao.query.Lang.HQL)) {
        hibernateQuery = hibernateSession.createQuery(bquery.getStatement());
      } else {
        hibernateQuery = hibernateSession.createSQLQuery(bquery.getStatement());
      }
      if (bquery.isCacheable()) {
        hibernateQuery.setCacheable(bquery.isCacheable());
      }
      setParameter(hibernateQuery, bquery.getParams());
      return hibernateQuery;
    }

    /**
     * 统计该查询的记录数
     * 
     * @param query
     * @param hibernateSession
     * @return
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
     * @param hibernateSession
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> find(final org.beangle.commons.dao.query.Query<T> query,
        final Session hibernateSession) {
      if (query instanceof LimitQuery<?>) {
        LimitQuery<T> limitQuery = (LimitQuery<T>) query;
        Query hibernateQuery = buildHibernateQuery(limitQuery, hibernateSession);
        if (null == limitQuery.getLimit()) {
          return hibernateQuery.list();
        } else {
          final PageLimit limit = limitQuery.getLimit();
          hibernateQuery.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
              limit.getPageSize());
          return hibernateQuery.list();
        }
      } else {
        return buildHibernateQuery(query, hibernateSession).list();
      }
    }

    /**
     * 为query设置参数
     * 
     * @param query
     * @param argument
     * @return
     */
    public static Query setParameter(final Query query, final Object[] argument) {
      if (argument != null && argument.length > 0) {
        for (int i = 0; i < argument.length; i++) {
          query.setParameter(i, argument[i]);
        }
      }
      return query;
    }

    /**
     * 为query设置参数
     * 
     * @param query
     * @param argument
     * @return
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
}
