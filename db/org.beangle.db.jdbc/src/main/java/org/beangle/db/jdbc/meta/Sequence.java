/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.beangle.db.jdbc.dialect.Dialect;

public class Sequence implements Comparable<Sequence> {

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
		if (null == dialect.getSequenceGrammar()) return null;
		String sql = dialect.getSequenceGrammar().getCreateSql();
		sql = StringUtils.replace(sql, ":name", name);
		sql = StringUtils.replace(sql, ":start", String.valueOf(current + 1));
		sql = StringUtils.replace(sql, ":increment", String.valueOf(increment));
		sql = StringUtils.replace(sql, ":cache", String.valueOf(cache));
		return sql;
	}

	public String sqlDropString(Dialect dialect) {
		if (null == dialect.getSequenceGrammar()) return null;
		String sql = dialect.getSequenceGrammar().getDropSql();
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

	public int compareTo(Sequence o) {
		return getName().compareTo(o.getName());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-64900959, -454788261).append(this.name).toHashCode();
	}

	/**
	 * 比较name
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(final Object object) {
		if (!(object instanceof Sequence)) { return false; }
		Sequence rhs = (Sequence) object;
		return getName().equals(rhs.getName());
	}
}
