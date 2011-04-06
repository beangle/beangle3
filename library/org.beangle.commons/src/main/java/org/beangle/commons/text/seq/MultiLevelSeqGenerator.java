/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public class MultiLevelSeqGenerator {

	private final Map<Integer, SeqPattern> patterns = CollectUtils.newHashMap();

	public SeqPattern getSytle(int level) {
		return patterns.get(level);
	}

	public String next(int level) {
		return getSytle(level).next();
	}

	public void add(SeqPattern style) {
		style.setGenerator(this);
		patterns.put(style.getLevel(), style);
	}

	public void reset(int level) {
		patterns.get(level).reset();
	}
}
