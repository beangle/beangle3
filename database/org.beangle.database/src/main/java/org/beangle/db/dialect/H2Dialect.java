/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

public class H2Dialect extends HSQLDialect {

	public H2Dialect() {
		super();
		ss.setQuerySequenceSql("select sequence_name,current_value,increment,cache from information_schema.sequences where sequence_schema=':schema'");
		ss.setCreateSql("create sequence :name start with :start increment by :increment cache :cache");
	}

}
