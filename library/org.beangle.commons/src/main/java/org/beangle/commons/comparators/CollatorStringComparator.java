/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.comparators;

import java.text.Collator;

public class CollatorStringComparator implements StringComparator {
	private boolean asc;

	private Collator collator;

	public CollatorStringComparator() {
		super();
		collator = Collator.getInstance();
	}

	public CollatorStringComparator(final boolean asc) {
		this();
		this.asc = asc;
	}

	public CollatorStringComparator(final boolean asc, final Collator collator) {
		this.collator = collator;
		this.asc = asc;
	}

	public int compare(final String what0, final String what1) {
		return (asc ? 1 : -1)
				* (collator.compare((null == what0) ? "" : what0, (null == what1) ? "" : what1));
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(final boolean asc) {
		this.asc = asc;
	}

	public Collator getCollator() {
		return collator;
	}

	public void setCollator(final Collator collator) {
		this.collator = collator;
	}

}
