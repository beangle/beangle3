/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import org.apache.commons.lang.StringUtils;
import org.beangle.db.dialect.Dialect;

public class Sequence {

	private String name;

	private long current;

	private int increment;

	private int cache;

	public Sequence() {
		super();
	}

	public Sequence(String name) {
		super();
		this.name = name;
		this.current = 0;
		this.increment = 1;
		this.cache = 32;
	}

	public String sqlCreateString(Dialect dialect) {
		if (null == dialect.getSequenceSupport()) return null;
		String sql = dialect.getSequenceSupport().getCreateSql();
		sql = StringUtils.replace(sql, ":name", name);
		sql = StringUtils.replace(sql, ":start", String.valueOf(current + 1));
		sql = StringUtils.replace(sql, ":increment", String.valueOf(increment));
		sql = StringUtils.replace(sql, ":cache", String.valueOf(cache));
		return sql;
	}

	public String sqlDropString(Dialect dialect) {
		if (null == dialect.getSequenceSupport()) return null;
		String sql = dialect.getSequenceSupport().getDropSql();
		sql = StringUtils.replace(sql, ":name", name);
		return sql;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStart() {
		return current + 1;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public int getCache() {
		return cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
