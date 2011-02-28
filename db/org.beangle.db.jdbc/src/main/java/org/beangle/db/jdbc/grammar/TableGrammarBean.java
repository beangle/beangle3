/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.grammar;

import org.apache.commons.lang.StringUtils;

public class TableGrammarBean implements TableGrammar {

	String nullColumnString = "";
	String comment;
	String columnComent;
	boolean supportsUnique = true;
	boolean supportsNullUnique = true;
	boolean supportsColumnCheck = true;

	String drop="drop table {}";
	
	String createString = "create table";

	public String getNullColumnString() {
		return nullColumnString;
	}

	public void setNullColumnString(String nullColumnString) {
		this.nullColumnString = nullColumnString;
	}

	public String getComment(String comment) {
		if (null == this.comment) return "";
		else return StringUtils.replace(this.comment, "{}", comment);
	}

	public String getColumnComment(String comment) {
		if (null == this.comment) return "";
		else return StringUtils.replace(this.comment, "{}", comment);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String tableComment) {
		this.comment = tableComment;
	}

	public String getColumnComent() {
		return columnComent;
	}

	public void setColumnComent(String columnComent) {
		this.columnComent = columnComent;
	}

	public boolean isSupportsUnique() {
		return supportsUnique;
	}

	public void setSupportsUnique(boolean supportsUnique) {
		this.supportsUnique = supportsUnique;
	}

	public boolean isSupportsNullUnique() {
		return supportsNullUnique;
	}

	public void setSupportsNullUnique(boolean supportsNullUnique) {
		this.supportsNullUnique = supportsNullUnique;
	}

	public boolean isSupportsColumnCheck() {
		return supportsColumnCheck;
	}

	public void setSupportsColumnCheck(boolean supportsColumnCheck) {
		this.supportsColumnCheck = supportsColumnCheck;
	}

	public String getCreateString() {
		return createString;
	}

	public void setCreateString(String createString) {
		this.createString = createString;
	}

	public String getDrop() {
		return drop;
	}

	public void setDrop(String drop) {
		this.drop = drop;
	}

	public String dropCascade(String table) {
		 return StringUtils.replace(this.drop, "{}", table);
	}
	
}
