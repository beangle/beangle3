/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.entity.Entity;

/**
 * dao 查询辅助类
 * 
 * @author chaostone
 */
public interface EntityDao {
  /**
   * 查询指定id的对象
   * 
   * @param clazz 类型
   * @param id 唯一标识
   * @return null
   */
  <T> T get(Class<T> clazz, Serializable id);

  /**
   * Returns model by identifier,null when not found.
   * 
   * @param entityName
   * @param id
   */
  <T> T get(String entityName, Serializable id);

  /**
   * Returns a list of all entity of clazz.
   * 
   * @param clazz
   */
  <T> List<T> getAll(Class<T> clazz);

  /**
   * 根据属性列举实体
   * 
   * @param entityClass
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, Long... values);

  /**
   * 根据属性列举实体
   * 
   * @param entityClass
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, Object... values);

  /**
   * 根据属性列举实体
   * 
   * @param entityClass
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, Collection<?> values);

  /**
   * 根据属性列举实体
   * 
   * @param entityClass
   * @param keyName
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, String keyName, Object... values);

  /**
   * 根据属性列举实体
   * 
   * @param <T>
   * @param entityClass
   * @param keyName
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, String keyName, Collection<?> values);

  /**
   * @param <T>
   * @param entityClass
   * @param attrs
   * @param values
   */
  <T> List<T> get(Class<T> entityClass, String[] attrs, Object... values);

  /**
   * @param <T>
   * @param entityClass
   * @param parameterMap
   */
  <T> List<T> get(Class<T> entityClass, Map<String, Object> parameterMap);

  /**
   * 根据属性列举实体
   * 
   * @param entityName
   * @param keyName
   * @param values
   */
  <T> List<T> get(String entityName, String keyName, Object... values);

  /**
   * 执行查询
   * 
   * @param query
   */
  <T> List<T> search(Query<T> query);

  /**
   * 查询hql语句
   * 
   * @param <T>
   * @param builder
   */
  <T> List<T> search(QueryBuilder<T> builder);

  /**
   * 查询hql语句
   * 
   * @param <T>
   * @param builder
   */
  <T> T uniqueResult(QueryBuilder<T> builder);

  /**
   * 命名查询
   * 
   * @param queryName
   * @param params
   */
  <T> List<T> searchNamedQuery(final String queryName, final Map<String, Object> params);

  /**
   * 命名查询
   * 
   * @param queryName
   * @param params
   */
  <T> List<T> searchNamedQuery(final String queryName, final Object... params);

  /**
   * 支持缓存的命名查询
   * 
   * @param queryName
   * @param params
   * @param cacheable
   */
  <T> List<T> searchNamedQuery(String queryName, Map<String, Object> params, boolean cacheable);

  /**
   * 直接查询
   * 
   * @param hql
   */
  <T> List<T> searchHQLQuery(final String hql);

  /**
   * HQL查询
   * 
   * @param hql
   * @param params
   */
  <T> List<T> searchHQLQuery(final String hql, final Map<String, Object> params);

  /**
   * HQL查询<br>
   * hql语句中使用?表示参数
   * 
   * @param hql
   * @param params
   */
  <T> List<T> searchHQLQuery(final String hql, final Object... params);

  /**
   * 支持缓存的HQL查询
   * 
   * @param hql
   * @param params
   * @param cacheable
   */
  <T> List<T> searchHQLQuery(String hql, final Map<String, Object> params, boolean cacheable);

  /**
   * 分页命名查询
   * 
   * @param queryName
   * @param params
   * @param limit
   */
  <T> Page<T> paginateNamedQuery(final String queryName, final Map<String, Object> params, PageLimit limit);

  /**
   * 分页HQL查询
   * 
   * @param hql
   * @param params
   * @param limit
   */
  <T> Page<T> paginateHQLQuery(final String hql, final Map<String, Object> params, PageLimit limit);

  /**
   * 执行HQL 进行更新或者删除
   * 
   * @param hql
   * @param arguments
   */
  int executeUpdateHql(String hql, Object... arguments);

  /**
   * 重复执行单个hql语句
   * 
   * @param queryStr
   * @param arguments
   */
  int executeUpdateHqlRepeatly(String queryStr, List<Object[]> arguments);

  /**
   * 执行HQL 进行更新或者删除
   * 
   * @param hql
   * @param parameterMap
   */
  int executeUpdateHql(String hql, Map<String, Object> parameterMap);

  /**
   * 执行命名语句进行更新或者删除
   * 
   * @param queryName
   * @param parameterMap
   */
  int executeUpdateNamedQuery(String queryName, Map<String, Object> parameterMap);

  /**
   * 执行命名语句进行更新或者删除
   * 
   * @param queryName
   * @param arguments
   */
  int executeUpdateNamedQuery(String queryName, Object... arguments);

  /**
   * 保存或更新单个或多个实体.
   */
  void saveOrUpdate(Object... entities);

  /**
   * 保存单个或多个实体.
   */
  void save(Object... entities);

  /**
   * Save Collection
   * 
   * @param entities
   */
  void saveOrUpdate(Collection<?> entities);

  /**
   * 按照实体名称，保存单个或多个实体.
   * 
   * @param entityName
   * @param entities
   */
  void saveOrUpdate(String entityName, Object... entities);

  /**
   * Save collection of given entity name.
   * 
   * @param entityName
   * @param entities
   */
  void saveOrUpdate(String entityName, Collection<?> entities);

  /**
   * Update entity's property value describe in upateParams where attr in
   * values.
   * 
   * @param entityClass
   * @param attr
   * @param values
   * @param updateParams
   */
  int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams);

  /**
   * Update entity set argumentName=argumentValue where attr in values.
   * 
   * @param entityClass
   * @param attr
   * @param values
   * @param argumentName
   * @param argumentValue
   */
  int update(Class<?> entityClass, String attr, Object[] values, String[] argumentName, Object[] argumentValue);

  /**
   * 删除单个对象
   * 
   * @param entities
   */
  void remove(Object... entities);

  /**
   * 删除集合内的所有对象
   * 
   * @param entities
   */
  void remove(Collection<?> entities);

  /**
   * 批量删除对象
   * 
   * @param entityClass 对象对应的类
   * @param attr 得到对象的key
   * @param values 要修改的values的值集合
   * @return 是否删除成功
   */
  boolean remove(Class<?> entityClass, String attr, Object... values);

  /**
   * 批量删除对象
   * 
   * @param entityClass
   *          (对象对应的类)
   * @param attr
   *          (得到对象的key)
   * @param values
   *          (要修改的ids的值集合)
   * @return 是否删除成功
   */
  boolean remove(Class<?> entityClass, String attr, Collection<?> values);

  /**
   * 批量删除对象
   * 
   * @param entityClass
   * @param parameterMap
   *          (取得对象的key的name和value对应的Map)
   * @return 是否删除成功
   */
  boolean remove(Class<?> entityClass, Map<String, Object> parameterMap);

  // Blob and Clob
  Blob createBlob(InputStream inputStream, int length);

  Blob createBlob(InputStream inputStream);

  Clob createClob(String str);

  // 容器相关
  void evict(Object entity);

  /**
   * Initialize entity whenever session close or open
   * 
   * @param <T>
   * @param entity
   */
  <T> T initialize(T entity);

  void refresh(Object entity);

  long count(String entityName, String keyName, Object value);

  long count(Class<?> entityClass, String keyName, Object value);

  long count(Class<?> entityClass, String[] attrs, Object[] values, String countAttr);

  boolean exist(Class<?> entityClass, String attr, Object value);

  boolean exist(String entityName, String attr, Object value);

  boolean exist(Class<?> entity, String[] attrs, Object[] values);

  boolean duplicate(String entityName, Serializable id, Map<String, Object> params);

  boolean duplicate(Class<? extends Entity<?>> clazz, Serializable id, String codeName, Object codeValue);

  /**
   * 在同一个session保存、删除
   * 
   * @param opts
   */
  void execute(Operation... opts);

  /**
   * 执行一个操作构建者提供的一系列操作
   * 
   * @param builder
   */
  void execute(Operation.Builder builder);
}
