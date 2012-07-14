/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.UnhandledException;

/**
 * <p>
 * CollectUtils class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class CollectUtils {

  /**
   * <p>
   * newArrayList.
   * </p>
   * 
   * @param <E> a E object.
   * @return a {@link java.util.List} object.
   */
  public static <E> List<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   * 
   * @param initialCapacity a int.
   * @param <E> a E object.
   * @return a {@link java.util.List} object.
   */
  public static <E> List<E> newArrayList(int initialCapacity) {
    return new ArrayList<E>(initialCapacity);
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   * 
   * @param c a {@link java.util.Collection} object.
   * @param <E> a E object.
   * @return a {@link java.util.List} object.
   */
  public static <E> List<E> newArrayList(Collection<? extends E> c) {
    return new ArrayList<E>(c);
  }

  /**
   * <p>
   * newArrayList.
   * </p>
   * 
   * @param values a E object.
   * @param <E> a E object.
   * @return a {@link java.util.List} object.
   */
  public static <E> List<E> newArrayList(E... values) {
    List<E> list = new ArrayList<E>(values.length);
    for (E e : values) {
      list.add(e);
    }
    return list;
  }

  /**
   * 将一个集合按照固定大小查分成若干个集合。
   * 
   * @param list a {@link java.util.List} object.
   * @param count a int.
   * @param <T> a T object.
   * @return a {@link java.util.List} object.
   */
  public static <T> List<List<T>> split(final List<T> list, final int count) {
    List<List<T>> subIdLists = CollectUtils.newArrayList();
    if (list.size() < count) {
      subIdLists.add(list);
    } else {
      int i = 0;
      while (i < list.size()) {
        int end = i + count;
        if (end > list.size()) {
          end = list.size();
        }
        subIdLists.add(list.subList(i, end));
        i += count;
      }
    }
    return subIdLists;
  }

  /**
   * <p>
   * newHashMap.
   * </p>
   * 
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link java.util.Map} object.
   */
  public static <K, V> Map<K, V> newHashMap() {
    return new HashMap<K, V>();
  }

  /**
   * <p>
   * newConcurrentHashMap.
   * </p>
   * 
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link java.util.Map} object.
   */
  public static <K, V> Map<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<K, V>();
  }

  /**
   * <p>
   * newConcurrentLinkedQueue.
   * </p>
   * 
   * @param <E> a E object.
   * @return a {@link java.util.Queue} object.
   */
  public static <E> Queue<E> newConcurrentLinkedQueue() {
    return new ConcurrentLinkedQueue<E>();
  }

  /**
   * <p>
   * newHashMap.
   * </p>
   * 
   * @param m a {@link java.util.Map} object.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link java.util.Map} object.
   */
  public static <K, V> Map<K, V> newHashMap(Map<? extends K, ? extends V> m) {
    return new HashMap<K, V>(m);
  }

  /**
   * <p>
   * newLinkedHashMap.
   * </p>
   * 
   * @param m a {@link java.util.Map} object.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link java.util.Map} object.
   */
  public static <K, V> Map<K, V> newLinkedHashMap(Map<? extends K, ? extends V> m) {
    return new LinkedHashMap<K, V>(m);
  }

  /**
   * <p>
   * newLinkedHashMap.
   * </p>
   * 
   * @param size a int.
   * @param <K> a K object.
   * @param <V> a V object.
   * @return a {@link java.util.Map} object.
   */
  public static <K, V> Map<K, V> newLinkedHashMap(int size) {
    return new LinkedHashMap<K, V>(size);
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   * 
   * @param <E> a E object.
   * @return a {@link java.util.Set} object.
   */
  public static <E> Set<E> newHashSet() {
    return new HashSet<E>();
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   * 
   * @param values a E object.
   * @param <E> a E object.
   * @return a {@link java.util.Set} object.
   */
  public static <E> Set<E> newHashSet(E... values) {
    Set<E> set = new HashSet<E>(values.length);
    for (E e : values) {
      set.add(e);
    }
    return set;
  }

  /**
   * <p>
   * newHashSet.
   * </p>
   * 
   * @param c a {@link java.util.Collection} object.
   * @param <E> a E object.
   * @return a {@link java.util.Set} object.
   */
  public static <E> Set<E> newHashSet(Collection<? extends E> c) {
    return new HashSet<E>(c);
  }

  /**
   * <p>
   * convertToMap.
   * </p>
   * 
   * @param coll a {@link java.util.Collection} object.
   * @param keyProperty a {@link java.lang.String} object.
   * @return a {@link java.util.Map} object.
   */
  public static Map<?, ?> convertToMap(Collection<?> coll, String keyProperty) {
    Map<Object, Object> map = newHashMap();
    for (Object obj : coll) {
      Object key = null;
      try {
        key = PropertyUtils.getProperty(obj, keyProperty);
      } catch (Exception e) {
        throw new UnhandledException(e);
      }
      map.put(key, obj);
    }
    return map;
  }

  /**
   * <p>
   * convertToMap.
   * </p>
   * 
   * @param coll a {@link java.util.Collection} object.
   * @param keyProperty a {@link java.lang.String} object.
   * @param valueProperty a {@link java.lang.String} object.
   * @return a {@link java.util.Map} object.
   */
  public static Map<?, ?> convertToMap(Collection<?> coll, String keyProperty, String valueProperty) {
    Map<Object, Object> map = newHashMap();
    for (Object obj : coll) {
      Object key = null;
      Object value = null;
      try {
        key = PropertyUtils.getProperty(obj, keyProperty);
        value = PropertyUtils.getProperty(obj, valueProperty);
      } catch (Exception e) {
        throw new UnhandledException(e);
      }
      map.put(key, value);
    }
    return map;
  }

  /**
   * <p>
   * toMap.
   * </p>
   * 
   * @param wordMappings an array of {@link java.lang.String} objects.
   * @return a {@link java.util.Map} object.
   */
  public static Map<String, String> toMap(String[]... wordMappings) {
    Map<String, String> mappings = new HashMap<String, String>();
    for (int i = 0; i < wordMappings.length; i++) {
      String singular = wordMappings[i][0];
      String plural = wordMappings[i][1];
      mappings.put(singular, plural);
    }
    return mappings;
  }
}
