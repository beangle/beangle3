/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.comparators;

import java.util.Comparator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * 组合比较器
 * 
 * @author chaostone
 */
public class ChainComparator<T> implements Comparator<T> {

	private List<Comparator<T>> comparators;

	public int compare(final T first, final T second) {
		int cmpRs = 0;
		for (final Comparator<T> com : comparators) {
			cmpRs = com.compare(first, second);
			if (0 == cmpRs) {
				continue;
			} else {
				break;
			}
		}
		return cmpRs;
	}

	public ChainComparator() {
		this.comparators = CollectUtils.newArrayList();
	}

	public ChainComparator(final List<Comparator<T>> comparators) {
		super();
		this.comparators = comparators;
	}

	public void addComparator(final Comparator<T> com) {
		this.comparators.add(com);
	}

	public List<Comparator<T>> getComparators() {
		return comparators;
	}

	public void setComparators(final List<Comparator<T>> comparators) {
		this.comparators = comparators;
	}

}
