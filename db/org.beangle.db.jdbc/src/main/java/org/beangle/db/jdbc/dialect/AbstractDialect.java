/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.grammar.SequenceGrammar;
import org.beangle.db.jdbc.grammar.TableGrammar;
import org.beangle.db.jdbc.grammar.TableGrammarBean;

public abstract class AbstractDialect implements Dialect {
	protected final TypeNames typeNames = new TypeNames();
	protected final SequenceGrammar ss;
	protected final LimitGrammar limitGrammar;
	protected final TableGrammar tableGrammar;
	protected final Dbversion version;
	protected final Set<String> keywords = CollectUtils.newHashSet();
	protected boolean caseSensitive = false;

	public AbstractDialect(String versions) {
		super();
		version = new Dbversion(versions);
		registerType();
		ss = buildSequenceGrammar();
		limitGrammar = buildLimitGrammar();
		tableGrammar = buildTableGrammar();
	}

	public TableGrammar getTableGrammar() {
		return tableGrammar;
	}

	public LimitGrammar getLimitGrammar() {
		return limitGrammar;
	}

	public SequenceGrammar getSequenceGrammar() {
		return ss;
	}

	public TypeNames getTypeNames() {
		return typeNames;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	protected abstract LimitGrammar buildLimitGrammar();

	protected TableGrammar buildTableGrammar() {
		return new TableGrammarBean();
	}

	protected abstract SequenceGrammar buildSequenceGrammar();

	protected abstract void registerType();

	protected void registerKeywords(String... words) {
		for (String word : words) {
			keywords.add(word.toLowerCase());
			keywords.add(word.toUpperCase());
		}
	}

	protected void registerType(int code, int capacity, String name) {
		typeNames.put(code, capacity, name);
	}

	protected void registerType(int code, String name) {
		typeNames.put(code, name);
	}

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
		StringBuffer res = new StringBuffer(30);
		res.append(" add constraint ").append(constraintName).append(" foreign key (")
				.append(StrUtils.join(foreignKey, ", ")).append(") references ").append(referencedTable);
		if (!referencesPrimaryKey) {
			res.append(" (").append(StrUtils.join(primaryKey, ", ")).append(')');
		}
		return res.toString();
	}

	public boolean supportsCascadeDelete() {
		return true;
	}

	public boolean support(String dbversion) {
		if (null != version) { return version.contains(dbversion); }
		return false;
	}

	public String defaultSchema() {
		return null;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

}
