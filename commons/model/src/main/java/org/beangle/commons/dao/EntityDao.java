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
package org.beangle.commons.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
  <T extends Entity<ID>, ID extends Serializable> T get(Class<T> clazz, ID id);

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
  <T extends Entity<?>> List<T> getAll(Class<T> clazz);

  /**
   * 根据属性列举实体
   *
   * @param entityClass
   * @param values
   */
  <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> entityClass, ID[] values);

  /**
   * 根据属性列举实体
   *
   * @param entityClass
   * @param values
   */
  <T extends Entity<ID>, ID extends Serializable> List<T> get(Class<T> entityClass, Collection<ID> values);

  /**
   * 根据属性列举实体
   *
   * @param clazz
   * @param keyName
   * @param values
   */
  <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Object... values);

  /**
   * 根据属性列举实体
   *
   * @param <T>
   * @param clazz
   * @param keyName
   * @param values
   */
  <T extends Entity<?>> List<T> get(Class<T> clazz, String keyName, Collection<?> values);

  /**
   * @param <T>
   * @param clazz
   * @param attrs
   * @param values
   */
  <T extends Entity<?>> List<T> get(Class<T> clazz, String[] attrs, Object... values);

  /**
   * @param <T>
   * @param clazz
   * @param parameterMap
   */
  <T extends Entity<?>> List<T> get(Class<T> clazz, Map<String, Object> parameterMap);

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
   * JPQL/NamedQuery<br>
   * query语句中使用?1表示参数.NamedQuery使用@Named-Query-Name
   *
   * @param query
   * @param params
   */
  <T> List<T> search(final String query, final Object... params);

  /**
   * JPQL/NamedQuery<br>
   *
   * @param query
   * @param params
   */
  <T> List<T> search(final String query, final Map<String, Object> params);

  /**
   * 支持缓存的JPQL/NamedQuery<br>
   * 查询
   *
   * @param query
   * @param params
   * @param limit
   * @param cacheable
   */
  <T> List<T> search(String query, final Map<String, Object> params, PageLimit limit, boolean cacheable);

  /**
   * 执行JPQL/NamedQuery 进行更新或者删除
   *
   * @param query
   * @param arguments
   */
  int executeUpdate(String query, Object... arguments);

  /**
   * 重复执行单个JPQL/NamedQuery语句
   *
   * @param query
   * @param arguments
   */
  int[]  executeUpdateRepeatly(String query, Collection<Object[]> arguments);

  /**
   * 执行JPQL/NamedQuery进行更新或者删除
   *
   * @param query
   * @param parameterMap
   */
  int executeUpdate(String query, Map<String, Object> parameterMap);

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
