/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence;

/**
 * @author cheneystar 2008-07-23
 */
public class TableSequence {

	/** sequence����� */
	private String seqName;

	/** ��Ӧ���� */
	private String tableName;

	/** ��Ӧ���� */
	private String idColumnName = "id";

	/** sequence�����һ��ִ�е����к� */
	private long lastNumber;

	/** ��Ӧ�������id */
	private long maxId;

	public long getLastNumber() {
		return lastNumber;
	}

	public void setLastNumber(long lastNumber) {
		this.lastNumber = lastNumber;
	}

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public long getMaxId() {
		return maxId;
	}

	public void setMaxId(long maxDataId) {
		this.maxId = maxDataId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setIdColumnName(String columnName) {
		this.idColumnName = columnName;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(seqName).append('(').append(lastNumber).append(')');
		buffer.append("  ");
		if (null == tableName) {
			buffer.append("----");
		} else {
			buffer.append(tableName).append('(').append(maxId).append(')');
		}
		return buffer.toString();
	}

}
