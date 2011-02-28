/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.grammar;

import org.apache.commons.lang.StringUtils;

public class LimitGrammarBean implements LimitGrammar {
	String pattern;
	String offsetPattern;
	boolean bindInReverseOrder;
	boolean bindFirst;
	boolean useMax;

	public LimitGrammarBean(String pattern, String offsetPattern, boolean bindInReverseOrder,
			boolean bindFirst, boolean useMax) {
		super();
		this.pattern = pattern;
		this.offsetPattern = offsetPattern;
		this.bindInReverseOrder = bindInReverseOrder;
		this.bindFirst = bindFirst;
		this.useMax = useMax;
	}

	public String limit(String query, boolean hasOffset) {
		if (hasOffset) {
			return StringUtils.replace(offsetPattern, "{}", query);
		} else {
			return StringUtils.replace(pattern, "{}", query);
		}
	}

	/**
	 * ANSI SQL defines the LIMIT clause to be in the form LIMIT offset, limit.
	 * Does this dialect require us to bind the parameters in reverse order?
	 * 
	 * @return true if the correct order is limit, offset
	 */
	public boolean isBindInReverseOrder() {
		return bindInReverseOrder;
	}

	public void setBindInReverseOrder(boolean bindInReverseOrder) {
		this.bindInReverseOrder = bindInReverseOrder;
	}

	public void setBindFirst(boolean bindFirst) {
		this.bindFirst = bindFirst;
	}

	public boolean isBindFirst() {
		return bindFirst;
	}

	public boolean isUseMax() {
		return useMax;
	}

	public void setUseMax(boolean useMaxForLimit) {
		this.useMax = useMaxForLimit;
	}

}
