/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.grammar.SequenceGrammar;
import org.beangle.db.jdbc.grammar.TableGrammar;

public interface Dialect {

	public TableGrammar getTableGrammar();

	public LimitGrammar getLimitGrammar();

	public SequenceGrammar getSequenceGrammar();

	public String defaultSchema();

	public TypeNames getTypeNames();

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey);

	public boolean supportsCascadeDelete();

}
