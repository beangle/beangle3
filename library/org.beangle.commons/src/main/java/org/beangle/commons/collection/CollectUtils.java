/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectUtils {

	public static <E> List<E> newArrayList() {
		return new ArrayList<E>();
	}

	public static <E> List<E> newArrayList(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	public static <E> List<E> newArrayList(Collection<? extends E> c) {
		return new ArrayList<E>(c);
	}

	/**
	 * 将一个集合按照固定大小查分成若干个集合。
	 * 
	 * @param list
	 * @param count
	 * @return
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

	public static <K, V> Map<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> Map<K, V> newHashMap(Map<? extends K, ? extends V> m) {
		return new HashMap<K, V>(m);
	}

	public static <K, V> Map<K, V> newLinkedHashMap(Map<? extends K, ? extends V> m) {
		return new LinkedHashMap<K, V>(m);
	}

	public static <K, V> Map<K, V> newLinkedHashMap(int size) {
		return new LinkedHashMap<K, V>(size);
	}

	public static <E> Set<E> newHashSet() {
		return new HashSet<E>();
	}

	public static <E> Set<E> newHashSet(Collection<? extends E> c) {
		return new HashSet<E>(c);
	}
}
