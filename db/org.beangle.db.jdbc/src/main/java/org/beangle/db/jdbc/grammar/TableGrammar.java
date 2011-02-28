/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.grammar;

public interface TableGrammar {

	public String getNullColumnString();

	public String getCreateString();

	public String getComment(String comment);

	public String dropCascade(String table);

	public String getColumnComment(String comment);

	public boolean isSupportsUnique();

	public boolean isSupportsNullUnique();

	public boolean isSupportsColumnCheck();
}
